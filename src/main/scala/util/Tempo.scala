/*
mls: basic machine learning algorithms for Scala
Copyright (C) 2014 Davi Pereira dos Santos

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

object Tempo {

  def timev[T](f: => T): (T, Double) = {
    val s = System.currentTimeMillis
    (f, (System.currentTimeMillis - s) / 1000d)
  }
  def time[T](f: => T): Double = {
    val s = System.currentTimeMillis
    f
    (System.currentTimeMillis - s) / 1000d
  }

  def now = System.currentTimeMillis

  var start_time = 0d

  def start {
    start_time = now
  }

  def print_stop {
    println("Elapsed " + (now - start_time) + "ms.")
  }

  def stop = {
    (now - start_time) / 1000d
  }

  def t[T](f: => T) = {
    val s = System.currentTimeMillis
    val r = f
    println("Elapsed " + (System.currentTimeMillis - s) + "ms.")
    r
  }

  def tn[T](f: => T) = {
    val s = System.currentTimeMillis
    val r = f
    print((System.currentTimeMillis - s) + " ")
    r
  }
}
