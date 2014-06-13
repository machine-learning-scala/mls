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

import weka.classifiers.Classifier
import ml.Pattern
import weka.classifiers.bayes.NaiveBayesUpdateable
import ml.models.Model

case class NBinc() extends IncrementalWekaLearner {
  //todo: something is wrong with updating, it should give the same results as batch version
  ???
  override val toString = "NBinc"

  def expected_change(model: Model)(pattern: Pattern): Double = ???

  def build(patterns: Seq[Pattern]): Model = {
    val classifier = new NaiveBayesUpdateable
    classifier.setUseSupervisedDiscretization(false)
    generate_model(classifier, patterns)
  }

  protected def test_subclass(cla: Classifier) = cla match {
    case n: NaiveBayesUpdateable => n
    case _ => throw new Exception(this +" requires NaiveBayesUpdateable.")
  }

  override def EMC(model: Model)(patterns: Seq[Pattern]): Pattern = ???
}