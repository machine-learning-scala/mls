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

  def safeQuit(msg: String, db: Lock = null) = {
    println(msg)

    //todo: essas linhas aqui supõe que sempre haverá uma thread de trabalho (query, hit por exemplo) capacitada a emitir o releaseOp(), do contrário, espera-se infinitamente
    if (db != null) db.acquireOp() else acquireOp() //passa direto
    running = false
    if (db != null) db.acquireOp() else acquireOp() //trava aqui até que um dos processos perceba que estamos em exiting (pode haver mais de um processo em paralelo nessa situação, somente o mais rápido tem garantias de usufruir)

    //Aguarda um pouco pra talvez pegar outros processos mais lentos. New jobs are blocked by an IF at their implementation (savequeries, savehits)
    println("Vou esperar 1h, caso veja que nada mais está rodando e deseje antecipar o destravamento das bases, crie um arquivo /tmp/unsafeQuit.davi .")
    def running2 = !new File("/tmp/unsafeQuit.davi").exists()
    var min = 0
    1 to 60 takeWhile { min => //1h
      Thread.sleep(60000) //1min.
      println(s"$min minutos corridos ")
      running2
    }

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

