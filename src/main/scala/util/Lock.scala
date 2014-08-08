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
  private val rnd = new Random(10)

  //semaphore
  var closeCounter = 0
  private var ava = true

  def incCounter(): Unit = {
    acq()
    closeCounter += 1
    rel()
  }

  def decCounter(): Unit = {
    acq()
    closeCounter -= 1
    rel()
  }

  private def acq() = {
    Thread.sleep((rnd.nextDouble() * 30).toInt)
    synchronized {
      while (!ava) wait()
      ava = false
    }
  }

  private def rel() = {
    Thread.sleep((rnd.nextDouble() * 30).toInt)
    synchronized {
      ava = true
      notify()
    }
  }

  //-----------------------------------------------

  val readOnly: Boolean
  var fileLocked: Boolean
  //  val threadsToWait: Int

  def close(): Unit

  def exiting() = !running

  def justQuit(str: String) = {
    println(str)
    sys.exit(1)
  }

  private var available = true
  private var availableOp = true
  var running = true

  def unsafeQuit(msg: String, db: Lock = null) = {
    if (db == null) close() else db.close()
    justQuit(s"Quiting (no waiting for other jobs): $msg")
  }

  // só há 4 términos para uma thread:
  // quando o serviço termina;
  // por safeQuit(), que aguarda outras threads, destrava arquivo db e apaga copyDb; bom quando há trabalho a ser gravado
  // por unsafeQuit(), que apenas destrava arquivo db e apaga copyDb; bom para encerrar quando a conexão foi aberta, mas não há trabalhos (p.ex. se fechou por erro)
  // por sys.exit(1), que interrompe tudo abruptamente, para bugs graves onde é melhor nem prosseguir com o programa em nenhum trabalho (p.ex. inconsistências por concorrência externa).
  def safeQuit(msg: String, db: Lock = null) = {
    println(s"Safe quiting (waiting for other jobs): $msg ...")

    //todo: essas linhas aqui supõem que sempre haverá um db (fazendo query ou hit por exemplo) capacitado a emitir o releaseOp() no close(), do contrário, espera-se infinitamente
    //interrompe todas as threads do db
    if (db != null) db.running = false else running = false

    //segura aqui enquanto houver threads terminando coisas importantes
    while (closeCounter > 0) Thread.sleep(1000)

    unsafeQuit("No more jobs to wait, no need for safe quiting.")
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
    incCounter()
    Thread.sleep((rnd.nextDouble() * 30).toInt)
    synchronized {
      while (!availableOp) wait()
      availableOp = false
    }
  }

  def releaseOp() = {
    decCounter()
    Thread.sleep((rnd.nextDouble() * 30).toInt)
    synchronized {
      availableOp = true
      notify()
    }
  }
}

