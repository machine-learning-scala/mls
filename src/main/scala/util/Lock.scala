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

  private val rnd = new Random(10)
  private var available = true
  var running = true

  def safeQuit(msg: String, db: Lock = null) = {
    println(msg)
    running = false
    if (db != null) {
      db.acquire()
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
}

