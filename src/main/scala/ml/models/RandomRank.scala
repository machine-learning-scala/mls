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
package ml.models

import ml.Pattern

import scala.util.Random

case class RandomRank(seed: Int) extends Model {
  def JS(pattern: Pattern) = ???

  def predict(instance: Pattern) = output(instance).zipWithIndex.maxBy(_._1)._2

  def distribution(instance: Pattern) = {
    println(s"distribution was not calculated with Platt/sigmoid")
    val arr = output(instance)
    val min = arr.min
    val max = arr.max
    val norm = arr map (x => x - min / max - min)
    val sum = norm.sum
    norm map (x => x / sum)
  }

  val rnd = new Random(seed)

  def output(instance: Pattern) = (rnd.shuffle(1 to instance.nclasses) map (_.toDouble)).toArray

  override lazy val L = ???
}
