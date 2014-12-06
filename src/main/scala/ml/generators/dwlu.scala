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

package ml.generators

object dwlu extends App {
   //   def f (x:Double) =math.sqrt(x)
   def f(x: Double) = 1 - 1 / (1 + math.sqrt(x))

  for (x <- -10 to 10 map (_ / 10d); y <- -10 to 10 map (_ / 10d)) {
    val d0 = {
      val dx = 1 - x
      val dy = 1 - y
       f(dx * dx + dy * dy)
    }
    val d1 = {
      val dx = 0.75 - x
      val dy = 1 - y
       f(dx * dx + dy * dy)
    }
    val d2 = {
      val dx = 0.75 - x
      val dy = 0.75 - y
       f(dx * dx + dy * dy)
    }

    val d0b = {
      val dx = -1 - x
      val dy = -1 - y
       f(dx * dx + dy * dy)
    }
    val d1b = {
      val dx = -0.75 - x
      val dy = -1 - y
       f(dx * dx + dy * dy)
    }
    val d2b = {
      val dx = -0.75 - x
      val dy = -0.75 - y
       f(dx * dx + dy * dy)
    }

    val d00 = {
      val dx = 0 - x
      val dy = 0 - y
       f(dx * dx + dy * dy)
    }

     val d = Seq(d0, d1, d2, d0b, d1b, d2b, d00).sorted.sum
     //    val d = Seq(d0, d1, d2, d0b, d1b, d2b, d00).sorted.take(4).sum
    println(s"$x $y $d")
  }
}
