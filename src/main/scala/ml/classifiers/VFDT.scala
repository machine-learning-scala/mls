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
import weka.classifiers.trees.HoeffdingTree

/**
 * Can VFDT be worse than VFDTBatch because of unavailability of all instances at the build of VFDT?
 * No, I dont think there is any optimized discretization.
 * GraceTime = math.min(200,nclasses + N/20)
 */
case class VFDT(notes: String = "semzscore") extends IncrementalWekaLearner {
  override val toString = s"VFDT_$notes"

  def expected_change(model: Model)(pattern: Pattern): Double = ???

  def build(patterns: Seq[Pattern]): Model = {
    val classifier = new HoeffdingTree
    classifier.setGracePeriod(math.max(math.min(200, patterns.head.nclasses), 3))
    generate_model(classifier, patterns)
  }

  override def update(model: Model, fast_mutable: Boolean = false)(pattern: Pattern) = {
    val newModel = super.update(model, fast_mutable)(pattern)
    val ht = test_subclass(newModel.classifier)
    ht.asInstanceOf[HoeffdingTree].setGracePeriod(math.min(200, pattern.nclasses + newModel.N / 20))
    newModel
  }

  protected def test_subclass(cla: Classifier) = cla match {
    case n: HoeffdingTree => n
    case x => throw new Exception(this + " requires HoeffdingTree. Not [" + x + "].")
  }

  override def EMC(model: Model)(patterns: Seq[Pattern]): Pattern = ???
}

object TestHTinc extends App {
  val d = Datasets.arff("/home/davi/wcs/ucipp/uci/banana.arff").right.get.toList
  val f = Datasets.zscoreFilter(d)
  val df = Datasets.applyFilterChangingOrder(d, f)
  val l = VFDTBatch()
  val linc = VFDT()

  //  Tempo.start
  //  var m = l.build(df.take(df.head.nclasses))
  //  df.drop(df.head.nclasses).foreach(p => m = l.update(m, fast_mutable = true)(p))
  //  Tempo.print_stop
  Tempo.start
  var minc = linc.build(df.take(df.head.nclasses))
  df.drop(df.head.nclasses).foreach(p => minc = linc.update(minc, fast_mutable = true)(p))
  Tempo.print_stop
  println()

  Tempo.start
  //  m.accuracy(df)
  Tempo.print_stop
  Tempo.start
  minc.accuracy(df)
  Tempo.print_stop
  println()

  Tempo.start
  //  val ma = m.accuracy(df)
  Tempo.print_stop
  Tempo.start
  val mia = minc.accuracy(df)
  Tempo.print_stop

  //  println(s"l:${ma} linc:${mia}")
  println(s"linc:${mia}")
}