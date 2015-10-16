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
import ml.models.{Model, WekaBatModel}
import util.Datasets
import weka.classifiers.Classifier
import weka.classifiers.`lazy`.IBk
import weka.core.neighboursearch.{KDTree, LinearNNSearch}
import weka.core.{ChebyshevDistance, EuclideanDistance, ManhattanDistance, MinkowskiDistance}

/**
 * Weights by (1-d) if desired.
 * @param k
 * @param distance_name
 * @param pattsForDistanceCache
 * @param notes
 * @param weighted
 */
case class KNNBatcha(k: Int, distance_name: String, pattsForDistanceCache: Seq[Pattern], weighted: Boolean = false) extends BatchWekaLearner {
  override val toString = k + "NN" + (if (distance_name == "manh") "m" else "")  + (if (weighted) "w" else "")
  //(if (weighted) " weighted " else " (") + distance_name + s")"
  val boundaryType = "flexível"
  val attPref = "numérico"
  val id = (if (distance_name == "manh") 124358 else 2) + (if (weighted) 0 else 200000)
  val abr = toString

  def build(patterns: Seq[Pattern]) = {
    lazy val instancesForCache = Datasets.patterns2instances(pattsForDistanceCache)
    val classifier = new IBk
    val distance = distance_name match {
      //      case "eucl" => new EuclideanDistance()
      //      case "mink" => new MinkowskiDistance() //defaults to EuclideanDistance   order: 1=manh; 2=eucl; infinity=cheb
      //      case "manh" => new ManhattanDistance()
      //      case "cheb" => new ChebyshevDistance()
      case "eucl" => new EuclideanDistance(instancesForCache)
      case "mink" => new MinkowskiDistance(instancesForCache) //defaults to EuclideanDistance   order: 1=manh; 2=eucl; infinity=cheb
      case "manh" => new ManhattanDistance(instancesForCache)
      case "cheb" => new ChebyshevDistance(instancesForCache)
    }
    val search = new LinearNNSearch //sabe lidar com NaNs/missingvalues
    //      val search = if (distance_name != "eucl" || pattsForDistanceCache.length / pattsForDistanceCache.head.nattributes < 10) new LinearNNSearch else new KDTree
    search.setDistanceFunction(distance)
    classifier.setNearestNeighbourSearchAlgorithm(search)
    classifier.setKNN(k)
    classifier.setDoNotCheckCapabilities(true)
    if (weighted) classifier.setOptions(weka.core.Utils.splitOptions("-F"))
    val instances = Datasets.patterns2instances(patterns)
    classifier.buildClassifier(instances)
    WekaBatModel(classifier, patterns)
  }

  def expected_change(model: Model)(pattern: Pattern): Double = ???

  protected def test_subclass(classifier: Classifier) = classifier match {
    case cla: IBk => cla
    case _ => throw new Exception(this + " requires IBk.")
  }
}
