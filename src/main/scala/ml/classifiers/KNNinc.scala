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
package ml.classifiers

import ml.Pattern
import ml.models.Model
import util.{Datasets, Tempo}
import weka.classifiers.Classifier
import weka.classifiers.`lazy`.IBk
import weka.core.neighboursearch.{KDTree, LinearNNSearch}
import weka.core.{ChebyshevDistance, EuclideanDistance, ManhattanDistance, MinkowskiDistance}

case class KNNinc(k: Int, distance_name: String, pattsForDistanceCache: Seq[Pattern], weighted: Boolean = false) extends IncrementalWekaLearner {
   override val toString = k + "NNinc" + (if (weighted) " weighted " else " (") + distance_name + s")"
   println("Please use KNNBatch which should have the same speed and is consistent across resumings.")
   val boundaryType = "flexível"
   val attPref = "numérico"
   val id = -1
   val abr = "kNNi"

   def build(patterns: Seq[Pattern]) = {
      lazy val instancesForCache = Datasets.patterns2instances(pattsForDistanceCache)
      val classifier = new IBk
      val distance = distance_name match {
         case "eucl" => new EuclideanDistance(instancesForCache)
         case "mink" => new MinkowskiDistance(instancesForCache) //defaults to EuclideanDistance   order: 1=manh; 2=eucl; infinity=cheb
         case "manh" => new ManhattanDistance(instancesForCache)
         case "cheb" => new ChebyshevDistance(instancesForCache)
      }
      val search = if (distance_name != "eucl" || pattsForDistanceCache.length / pattsForDistanceCache.head.nattributes < 10) new LinearNNSearch else new KDTree
      //    val search = new KDTree()
      search.setDistanceFunction(distance)
      classifier.setNearestNeighbourSearchAlgorithm(search)
      classifier.setKNN(k)
      if (weighted) classifier.setOptions(weka.core.Utils.splitOptions("-I"))
      generate_model(classifier, patterns)
   }

   def expected_change(model: Model)(pattern: Pattern): Double = ???

   protected def test_subclass(classifier: Classifier) = classifier match {
      case cla: IBk => cla
      case _ => throw new Exception(this + " requires IBk.")
   }
}