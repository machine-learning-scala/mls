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

import scala.collection.immutable.Map

case class FakeModelRank(predMap: Map[Int, Array[Double]]) extends Model {
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

  def output(instance: Pattern) = predMap(instance.id)

  /**
   * All maps should have the same ids.
   * @param that
   */
  def +(that: FakeModelRank) = FakeModelRank(flatten(List(predMap, that.predMap)))

  /**
   * Não pode haver intersecção.
   * @param that
   * @return
   */
  def ++(that: FakeModelRank) = FakeModelRank(predMap ++ that.predMap)

  /**
   * Reduz list de mapa pra um só.
   * All maps should have the same ids.
   * @param pm
   * @return
   */
  def flatten(pm: List[Map[Int, Array[Double]]]) = pm.filter(_.nonEmpty) match {
    case List() => ???
    case List(x) => x
    case l =>
      l.reduce((a, b) => a.toList.sortBy(_._1).zip(b.toList.sortBy(_._1)).map {
        case (x, y) =>
          if (x._1 != y._1) ???
          x._1 -> x._2.zip(y._2).map(x => x._1 + x._2)
      }.toMap)
  }

  def normRank(ranking: Array[Double]) = {
    val nclasses = ranking.size
    val (min, max) = ranking.min -> ranking.max
    ranking.map(x => 1 + (nclasses - 1) * (x - min) / (max - min))
  }

  def normalized = FakeModelRank(predMap map { case (i, ar) => i -> normRank(ar) })
}
