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

import scala.util.Random

object fourclusters extends App {

  val resol = 30

  def bola(cx: Double, cy: Double, r: Double, l: Int) {
    for (x <- -resol to resol; y <- -resol to resol) {
      val dx = x / resol.toDouble - cx
      val dy = y / resol.toDouble - cy
      //      println(dx+" "+dy)
      if (math.sqrt(dx * dx + dy * dy) < r) println((Random.nextGaussian() * 0.01 + x / resol.toDouble) + "," + (Random.nextGaussian() * 0.01 + y / resol.toDouble) + "," + l)
    }
  }

  def bola2(cx: Double, cy: Double, r: Double) {
    for (x <- -resol to resol; y <- -resol to resol) {
      val dx = x / resol.toDouble - cx
      val dy = y / resol.toDouble - cy
      //      println(dx+" "+dy)
      if (x < 0 && math.sqrt(dx * dx + dy * dy) < r) println((Random.nextGaussian() * 0.01 + x / resol.toDouble) + "," + (Random.nextGaussian() * 0.01 + y / resol.toDouble) + "," + 0)
      if (x >= 0 && math.sqrt(dx * dx + dy * dy) < r) println((Random.nextGaussian() * 0.01 + x / resol.toDouble) + "," + (Random.nextGaussian() * 0.01 + y / resol.toDouble) + "," + 1)
    }
  }

  1 to 3 foreach { _ =>
    bola(0.9, 0.9, 0.12, 0)
    bola(0.6, 0.9, 0.12, 0)
    bola(0.9, 0.6, 0.12, 0)
    bola(0.6, 0.6, 0.12, 0)

    bola(-0.6, -0.6, 0.12, 0)

    bola(0, 0, 0.33, 0)
    bola(0, 0, 0.33, 0)
    bola(0, 0, 0.33, 0)

    bola(-0.9, 0.9, 0.12, 1)
    bola(-0.6, 0.9, 0.12, 1)
    bola(-0.9, 0.6, 0.12, 1)
    bola(-0.6, 0.6, 0.12, 1)

    bola(-0.9, -0.9, 0.12, 1)
    bola(-0.6, -0.9, 0.12, 1)
    bola(-0.9, -0.6, 0.12, 1)
    bola(-0.5, -0.5, 0.12, 1)

    bola(0.9, -0.9, 0.12, 1)
    bola(0.6, -0.9, 0.12, 1)
    bola(0.9, -0.6, 0.12, 1)
    bola(0.6, -0.6, 0.12, 1)
  }
  //  bola(-0.7, -0.7, 0.3, 1)
  //  bola2(0, 0, 0.5)
  //  bola(-1, 1, 0.5, 0)
}
