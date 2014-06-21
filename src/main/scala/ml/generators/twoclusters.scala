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

object twoclusters extends App {
  for (x <- -10 to 10 map (_ / 10d); y <- -10 to 10 map (_ / 10d)) {
    val dx = x - 0.5
    val dy = y - 0.5
    if (math.sqrt(dx * dx + dy * dy) < 0.25) println(x + "," + y + "," + 0) //else println(x + "," + y + "," + 0)
  }
  for (x <- -10 to 10 map (_ / 10d); y <- -10 to 10 map (_ / 10d)) {
    val dx = x + 0.5
    val dy = y + 0.5
    if (math.sqrt(dx * dx + dy * dy) < 0.25) println(x + "," + y + "," + 1) //else println(x + "," + y + "," + 0)
  }
}
