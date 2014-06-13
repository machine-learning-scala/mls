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

import weka.classifiers.`lazy`.IBk
import weka.core.neighboursearch.LinearNNSearch
import weka.core.{ChebyshevDistance, ManhattanDistance, MinkowskiDistance, EuclideanDistance}
import ml.Pattern
import ml.models.{WekaBatModel, Model}
import weka.classifiers.Classifier
import util.Datasets

case class KNN(k: Int, distance_name: String, weighted: Boolean = false) extends BatchWekaLearner {
  override val toString = k + "NN" + (if (weighted) "w" else "_") + distance_name

  def build(patterns: Seq[Pattern]) = {
    val classifier = new IBk
    val distance = distance_name match {
      case "eucl" => new EuclideanDistance
      case "mink" => new MinkowskiDistance //defaults to EuclideanDistance   order: 1=manh; 2=eucl; infinity=cheb
      case "manh" => new ManhattanDistance
      case "cheb" => new ChebyshevDistance
    }
    val search = new LinearNNSearch //if (training_set.length / training_set.head.nattributes < 10) new LinearNNSearch else new KDTree
    search.setDistanceFunction(distance)
    classifier.setNearestNeighbourSearchAlgorithm(search)
    classifier.setKNN(k)
    if (weighted) classifier.setOptions(weka.core.Utils.splitOptions("-I"))
    val instances = Datasets.patterns2instances(patterns)
    classifier.buildClassifier(instances)
    WekaBatModel(classifier, patterns)
  }

  def expected_change(model: Model)(pattern: Pattern): Double = ???

  protected def test_subclass(classifier: Classifier)=classifier match {
    case cla: IBk => cla
    case _ => throw new Exception(this + " requires IBk.")
  }

  override def EMC(model: Model)(patterns: Seq[Pattern]): Pattern = ???
}
