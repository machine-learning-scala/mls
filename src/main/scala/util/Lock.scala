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

import scala.util.Random

trait Lock {
  val readOnly: Boolean
  var fileLocked: Boolean

  def hardClose(): Unit
  private val rnd = new Random(10)
  private var available = true

  def safeQuit(msg: String, db: Lock = null) = {
    println(msg)
    if (db != null) {
      if (!db.readOnly) db.acquire()
      if (fileLocked) db.hardClose()
      else {
        //saida completa quando não se trata de problema de concorrência: acquire, conn, apaga copy, unlock, exit
        db.acquire()
        sys.exit(1)
      }
    }

    if (!readOnly) acquire()
    if (fileLocked) hardClose()
    else {
      //saida completa quando não se trata de problema de concorrência: acquire, conn, apaga copy, unlock, exit
      acquire()
      sys.exit(1)
    }

    println("Safe quit!")
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
}

