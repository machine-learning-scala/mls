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

import util.{Tempo, Datasets}
import weka.classifiers.Classifier
import ml.Pattern
import weka.classifiers.bayes.NaiveBayesUpdateable
import ml.models.Model

/**
 * NBinc can be worse than NB because of unavailability of all instances at the build of NBinc.
 * Since NB does rebuild every new instance, it can perform optimized discretization at all calls to update().
 */
case class NBinc() extends IncrementalWekaLearner {
  override val toString = "NBinc"

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
  val df = Datasets.applyFilter(d, f)
  val l = NB()
  val linc = NBinc()

  var m = l.build(df.take(1))
  var minc = linc.build(df.take(1))

  Tempo.start
  df.drop(1).foreach(p => m = l.update(m,fast_mutable = true)(p))
  Tempo.print_stop

  Tempo.start
  df.drop(1).foreach(p => minc = linc.update(minc,fast_mutable = true)(p))
  Tempo.print_stop

  println(s"l:${m.accuracy(df)} linc:${minc.accuracy(df)}")
}