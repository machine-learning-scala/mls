/*
 active-learning-scala: Active Learning library for Scala
 Copyright (c) 2014 Davi Pereira dos Santos

   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package util

import java.io.File

import scala.util.Random

trait Lock {
  val readOnly: Boolean
  var fileLocked: Boolean

  def hardClose(): Unit

  def exiting() = !running

  private val rnd = new Random(10)
  private var available = true
  private var availableOp = true
  var running = true

  def unsafeQuit(msg: String, db: Lock = null) = {

  }

  def safeQuit(msg: String, db: Lock = null) = {
    println(msg)

    //todo: essas linhas aqui supõem que sempre haverá um db (fazendo query ou hit por exemplo) capacitado a emitir o releaseOp(), do contrário, espera-se infinitamente
    if (db != null) db.running = false else running = false //interrompe todas as threads do db; elas já acquireOp() durante open()
    if (db != null) db.acquireOp() //threads interrompidas esperam aqui até que as restantes cheguem ao db.close() em comum que é o único releaseOp() existente; safeQuit chamado de dentro do db não precisa aguardar close(), pois não vai existir
    // só há 4 términos para uma thread:
    // quando o serviço termina;
    // por safeQuit(), que aguarda outras threads, destrava arquivo db e apaga copyDb;
    // por unsafeQuit(), que apenas destrava arquivo db e apaga copyDb;
    // por sys.exit(1), que interrompe tudo abruptamente, para bugs que não afetem outras threads.

    //se todas as threads chamarem safequit, então pode-se e deve-se sair imediatamente

    if (db != null) {
      db.acquire() //aguarda caso ainda haja algo importante rolando (disco , ...)
      if (!db.readOnly) {
        db.hardClose()
        println("Safe quit 1!!")
      } else println("violent quit 1")
    } else {
      acquire() //aguarda caso haja algo importante rolando (disco , ...)
      if (!readOnly) {
        hardClose()
        println("Safe quit 2!!")
      }
      else println("violent quit 2")
    }
    sys.exit(1)
  }

  def acquire() = {
    Thread.sleep((rnd.nextDouble() * 30).toInt)
    synchronized {
      while (!available) wait()
      available = false
    }
  }

  def release() = {
    Thread.sleep((rnd.nextDouble() * 30).toInt)
    synchronized {
      available = true
      notify()
    }
  }

  def acquireOp() = {
    //    Thread.sleep((rnd.nextDouble() * 30).toInt)
    synchronized {
      while (!availableOp) wait()
      availableOp = false
    }
  }

  def releaseOp() = {
    //    Thread.sleep((rnd.nextDouble() * 30).toInt)
    synchronized {
      availableOp = true
      notify()
    }
  }
}

