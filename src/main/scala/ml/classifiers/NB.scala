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
import weka.classifiers.bayes.NaiveBayesUpdateable

/**
 * Can NB be worse than NBBatch because of unavailability of all instances at the build of NB?
 * No, because it discretizes by fixed intervals of 0.1 (need to check in weka sources).
 */
case class NB(notes: String = "") extends IncrementalWekaLearner {
  override val toString = s"NB_$notes"

  def expected_change(model: Model)(pattern: Pattern): Double = ???

  def build(patterns: Seq[Pattern]): Model = {
    val classifier = new NaiveBayesUpdateable
    classifier.setUseSupervisedDiscretization(false)
    generate_model(classifier, patterns)
  }

  protected def test_subclass(cla: Classifier) = cla match {
    case n: NaiveBayesUpdateable => n
    case _ => throw new Exception(this + " requires NaiveBayesUpdateable.")
  }

  override def EMC(model: Model)(patterns: Seq[Pattern]): Pattern = ???
}

object TestNBinc extends App {
  val d = Datasets.arff(bina = true)("/home/davi/wcs/ucipp/uci/abalone-11class.arff", zscored = false).right.get.toList
  val f = Datasets.zscoreFilter(d)
  val df = Datasets.applyFilterChangingOrder(d, f)
  val l = NBBatch()
  val linc = NB()

  var m = l.build(df.take(df.head.nclasses))
  var minc = linc.build(df.take(df.head.nclasses))
  val fast_mutable = true

  println(s"only build     l:${m.accuracy(df)} linc:${minc.accuracy(df)}")

  //  df.drop(df.head.nclasses).foreach { p =>
  //    m = l.update(m, fast_mutable)(p)
  //    minc = linc.update(minc, fast_mutable)(p)
  //    println(s"updating      l:${m.accuracy(df)} linc:${minc.accuracy(df)}")
  // }

  Tempo.start
  df.drop(df.head.nclasses).foreach(p => m = l.update(m, fast_mutable)(p))
  Tempo.print_stop

  Tempo.start
  df.drop(df.head.nclasses).foreach(p => minc = linc.update(minc, fast_mutable)(p))
  Tempo.print_stop

  println(s"after updates      l:${m.accuracy(df)} linc:${minc.accuracy(df)}")

  //  Tempo.start
  //  df.drop(10).foreach(p => minc = linc.update(minc, fast_mutable = false)(p))
  //  Tempo.print_stop
  //
  //  println(s"after unlearning     l:${m.accuracy(df)} linc:${minc.accuracy(df)}")
}