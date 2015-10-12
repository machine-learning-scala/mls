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
   def JS(pattern: Pattern): Double

   def predict(instance: Pattern): Double

   /**
    * Mostra qtas vezes a classe da linha foi predita como a classe da coluna.
    * @param patts
    */
   def confusion(patts: Seq[Pattern]) = if (patts.isEmpty) {
      println("Empty list of patterns at confusion matrix.")
      sys.exit(1)
   } else {
      val nc = patts.head.nclasses
      val n = patts.size
      val res = Array.fill(nc)(Array.fill(nc)(0))
      var i = 0
      while (i < n) {
         val p = patts(i)
         res(p.label.toInt)(predict(p).toInt) += 1
         i += 1
      }
      res
   }

   def distribution(instance: Pattern): Array[Double]

   protected def log(x: Double) = if (x == 0) 0d else math.log(x)

   protected def normalized_entropy(P: Array[Double]) = -P.map(x => x * log(x)).sum / log(P.length)

   protected def media_desvioPadrao(items: Vector[Double]) = {
      val s = items.sum
      val l = items.length.toDouble
      val m = s / l
      val v0 = (items map {
         x =>
            val di = x - m
            di * di
      }).sum / (l - 1)
      val v = if (v0.isNaN) 0 else v0
      val d = math.sqrt(v)
      (m, d)
   }

   def predictionEntropy(patts: Seq[Pattern]) = if (patts.isEmpty) {
      println("Empty list of patterns at predictionEntropy.")
      sys.exit(1)
   } else {
      val ents = patts.map(x => normalized_entropy(distribution(x)))
      media_desvioPadrao(ents.toVector)
   }

   def output(instance: Pattern): Array[Double]

   //  {
   //    val dist = distribution(instance)
   //    val nclasses = instance.nclasses
   //    var c = 0
   //    var max = 0d
   //    var cmax = 0
   //    while (c < nclasses) {
   //      val v = dist(c)
   //      if (v > max) {
   //        max = v
   //        cmax = c
   //      }
   //      c += 1
   //    }
   //    cmax
   //  }

   def hit(instance: Pattern) = instance.label == predict(instance)

   def hits(patterns: Seq[Pattern]) = patterns.count(hit) //weka is not thread-safe to parallelize hits()

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

//trait IncrementalModel extends Model

trait BatchModel extends Model {
   val training_set: Vector[Pattern]
}


//  val size: Double

//  def distributions(instance: Pattern): Seq[Array[Double]]

//  def distribution(instance: Pattern) = {
//    val dists = distributions(instance)
//    val dist = dists(0)
//    val nclasses = instance.nclasses
//    var c = 0
//    while (c < nclasses) {
//      var d = 1
//      while (d < size) {
//        dist(c) += dists(d)(c)
//        d += 1
//      }
//      dist(c) /= size
//      c += 1
//    }
//    dist
//    //    distributions(instance).transpose.map(_.sum / size).toArray
//  } //average between distributions (is it the same as adding and normalizing?)

//  /**
//   * Hard prediction for a given instance.
//   * In the case of ensembles, hard vote will be performed.
//   * @param instance
//   * @return
//   */
//  def predict(instance: Pattern) = {
//    val dists = distributions(instance) //weka classifyInstance() also internally falls back to distributionForInstance()
//    val nclasses = instance.nclasses
//    val votes = new Array[Int](nclasses)
//    var d = 0
//    while (d < size) {
//      var c = 0
//      var max = 0d
//      var cmax = 0
//      while (c < nclasses) {
//        val v = dists(d)(c)
//        if (v > max) {
//          max = v
//          cmax = c
//        }
//        c += 1
//      }
//      votes(cmax) += 1
//      d += 1
//    }
//    var c = 0
//    var max = 0
//    var cmax = 0
//    while (c < nclasses) {
//      val v = votes(c)
//      if (v > max) {
//        max = v
//        cmax = c
//      }
//      c += 1
//    }
//    cmax
//  }
