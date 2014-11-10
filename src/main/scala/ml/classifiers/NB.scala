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
case class NB() extends IncrementalWekaLearner {
  override val toString = s"NB"
  val id = 1
  val abr = toString

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
