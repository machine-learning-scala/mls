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

case class KNN(k: Int, distance_name: String, pattsForDistanceCache: Seq[Pattern], notes: String = "", weighted: Boolean = false) extends IncrementalWekaLearner {
  override val toString = k + "NN" + (if (weighted) " weighted " else " (") + distance_name + s")_$notes"

  def build(patterns: Seq[Pattern]) = {
    lazy val instancesForCache = Datasets.patterns2instances(pattsForDistanceCache)
    val classifier = new IBk
    val distance = distance_name match {
      case "eucl" => new EuclideanDistance(instancesForCache)
      case "mink" => new MinkowskiDistance(instancesForCache) //defaults to EuclideanDistance   order: 1=manh; 2=eucl; infinity=cheb
      case "manh" => new ManhattanDistance(instancesForCache)
      case "cheb" => new ChebyshevDistance(instancesForCache)
    }
    val search = if (distance_name != "eucl" || patterns.length / patterns.head.nattributes < 10) new LinearNNSearch else new KDTree
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

  override def EMC(model: Model)(patterns: Seq[Pattern]): Pattern = ???
}


object TestKNN extends App {
  val d = Datasets.arff(bina = true)("/home/davi/wcs/ucipp/uci/abalone-11class.arff", zscored = false).right.get.toList
  val f = Datasets.zscoreFilter(d)
  val df = Datasets.applyFilterChangingOrder(d, f)
  lazy val l = KNNBatch(5, "eucl", df)
  lazy val linc = KNN(5, "eucl", df)

  Tempo.start
  var m = l.build(df.take(df.head.nclasses))
  df.drop(df.head.nclasses).foreach(p => m = l.update(m, fast_mutable = true)(p))
  Tempo.print_stop
  Tempo.start
  var minc = linc.build(df.take(df.head.nclasses))
  df.drop(df.head.nclasses).foreach(p => minc = linc.update(minc, fast_mutable = true)(p))
  Tempo.print_stop
  println()

  Tempo.start
  m.accuracy(df)
  Tempo.print_stop
  Tempo.start
  minc.accuracy(df)
  Tempo.print_stop
  println()

  Tempo.start
  val ma = m.accuracy(df)
  Tempo.print_stop
  Tempo.start
  val mia = minc.accuracy(df)
  Tempo.print_stop

  println(s"l:${ma} linc:${mia}")
}