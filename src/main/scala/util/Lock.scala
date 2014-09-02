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

import java.io.{InputStream, FileInputStream, File}

import scala.io.Source
import scala.util.Random

trait Lock {
  val runs = Source.fromFile("runs.txt").getLines().toList.head.toInt
  val folds = Source.fromFile("folds.txt").getLines().toList.head.toInt
  val folderToCopyDb = Source.fromFile("dbcopy.txt").getLines().toList.head
  val fileToStopProgramUnsafe = "/tmp/unsafeQuit.davi"
  val rnd = new Random(System.nanoTime())

  def checkExistsForNFS(f: File, delay: Int = 300) = {
    Thread.sleep((rnd.nextDouble() * delay).toInt)
    try {
      val buffer = new Array[Byte](4)
      val is = new FileInputStream(f)
      if (is.read(buffer) != buffer.length) {
        print("   nonecxiste   ")
      }
      is.close()
      true
    } catch {
      case _: Throwable => false
    }
  }

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

  def close()

  def isOpen(): Boolean

  def exiting() = !running

  def justQuit(str: String) = {
    //    running = false
    println(str)
    sys.exit(1)
  }

  private var available = true
  private var availableOp = true
  private var availableOp2 = true
  var running = true

  def unsafeQuit(msg: String) = {
    if (isOpen()) close()
    justQuit(s"Quiting : $msg")
  }

  // só há 4 términos para uma thread:
  // quando o serviço termina;
  // por safeQuit(), que aguarda outras threads, destrava arquivo db e apaga copyDb; bom quando há trabalho a ser gravado
  // por unsafeQuit(), que apenas destrava arquivo db e apaga copyDb; bom para encerrar quando a conexão foi aberta, mas não há trabalhos (p.ex. se fechou por erro)
  // por justQuit(1), que interrompe tudo abruptamente, para bugs graves onde é melhor nem prosseguir com o programa em nenhum trabalho (p.ex. inconsistências por concorrência externa).
  def safeQuit(msg: String) = {
    println(s"Safe quiting (waiting for $closeCounter other jobs): $msg ...")

    //interrompe todos os loops dentro de threads do db
    running = false

    //segura aqui enquanto houver threads terminando coisas importantes
    val tmpLockingFileUnsafe = new File(fileToStopProgramUnsafe)
    while (closeCounter > 0 && !tmpLockingFileUnsafe.exists()) {
      Thread.sleep(3000)
      println(s"Waiting for $closeCounter other jobs to quit ...")
    }

    if (checkExistsForNFS(tmpLockingFileUnsafe)) {
      tmpLockingFileUnsafe.delete()
      unsafeQuit(s"$tmpLockingFileUnsafe file found! Giving up waiting for $closeCounter jobs...")
    } else unsafeQuit("No more jobs to wait!")
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
    Thread.sleep((rnd.nextDouble() * 30).toInt)
    synchronized {
      while (!availableOp) {
        //        println("waiting")
        //        Thread.sleep(1000)
        wait()
      }
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

  def acquireOp2() = {
    Thread.sleep((rnd.nextDouble() * 30).toInt)
    synchronized {
      while (!availableOp2) wait()
      availableOp2 = false
    }
  }

  def releaseOp2() = {
    Thread.sleep((rnd.nextDouble() * 30).toInt)
    synchronized {
      availableOp2 = true
      notify()
    }
  }
}

object ExistsTest extends Lock with App {
  val readOnly: Boolean = true

  def isOpen() = ???

  def close() = ???

  var fileLocked: Boolean = _

  println(checkExistsForNFS(new File("/tmp/asd")))
}