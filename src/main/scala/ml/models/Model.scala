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

trait Model {

  val size: Double

  def distributions(instance: Pattern): Seq[Array[Double]]

  def distribution(instance: Pattern) = {
    val dists = distributions(instance)
    val dist = dists(0)
    val nclasses = instance.nclasses
    var c = 0
    while (c < nclasses) {
      var d = 1
      while (d < size) {
        dist(c) += dists(d)(c)
        d += 1
      }
      dist(c) /= size
      c += 1
    }
    dist
    //    distributions(instance).transpose.map(_.sum / size).toArray
  } //average between distributions (is it the same as adding and normalizing?)

  /**
   * Hard prediction for a given instance.
   * In the case of ensembles, hard vote will be performed.
   * @param instance
   * @return
   */
  def predict(instance: Pattern) = {
    val dists = distributions(instance) //weka classifyInstance() also internally falls back to distributionForInstance()
    val nclasses = instance.nclasses
    val votes = new Array[Int](nclasses)
    var d = 0
    while (d < size) {
      var c = 0
      var max = 0d
      var cmax = 0
      while (c < nclasses) {
        val v = dists(d)(c)
        if (v > max) {
          max = v
          cmax = c
        }
        c += 1
      }
      votes(cmax) += 1
      d += 1
    }
    var c = 0
    var max = 0
    var cmax = 0
    while (c < nclasses) {
      val v = votes(c)
      if (v > max) {
        max = v
        cmax = c
      }
      c += 1
    }
    cmax

    //    val votes = Array.fill(instance.nclasses)(0)
    //    distributions(instance).foreach(dist => votes(dist.zipWithIndex.maxBy(_._1)._2) += 1)
    //    //    println(votes.toList + " " + votes.zipWithIndex.maxBy(_._1)._2)
    //    votes.zipWithIndex.maxBy(_._1)._2
  }

  /**
   * Soft ensemble prediction for a given instance.
   * In the case of a single model, it is the same as predict() but less efficient.
   * @param instance
   * @return
   */
  def softPredict(instance: Pattern) = {
    distribution(instance).zipWithIndex.maxBy(_._1)._2
  }

  //  def predict(instance: Pattern) = distribution(instance).zipWithIndex.maxBy(_._1)._2

  def hit(instance: Pattern) = {
    //    println(instance.label + "  " + instance.label_array.toList)
    instance.label == predict(instance)
  }

  def hits(patterns: Seq[Pattern]) = patterns.par.count(hit)

  def accuracy(patterns: Seq[Pattern], n: Double = -1) = {
    hits(patterns) / (if (n == -1) patterns.length.toDouble else n)
  }

  def hits_and_qtd_per_class(patterns: Seq[Pattern]) = {
    ??? //inefficient
    (0 until patterns.head.nclasses) map {
      c =>
        val hits_for_this_class = patterns.filter(_.label == c)
        val hits = (hits_for_this_class map hit) count (_ == true)
        (hits, hits_for_this_class.length)
    }
  }
}

trait IncrementalModel extends Model

trait BatchModel extends Model {
  val training_set: IndexedSeq[Pattern]
}

