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
import weka.classifiers.Classifier
import weka.classifiers.bayes.NaiveBayes
import weka.classifiers.meta.{Bagging, RotationForest}
import weka.classifiers.trees.RandomTree

case class BagNB(seed: Int = 42, iterations: Int = 10) extends BatchWekaLearner {
  override val toString = s"BagNB" + (if (iterations != 10) iterations else "")
  val boundaryType = "flexível"
  val attPref = "nominal"
  val id = 54321
  val abr = toString

  def expected_change(model: Model)(pattern: Pattern): Double = ???

  def build(patterns: Seq[Pattern]): Model = {
    val classifier = new Bagging
    classifier.setSeed(seed)
    classifier.setNumIterations(iterations)
    classifier.setDebug(false)
    classifier.setDoNotCheckCapabilities(true)

    val cla = new NaiveBayes
    cla.setDebug(false)
    cla.setUseSupervisedDiscretization(true) //true=slow?
    cla.setDoNotCheckCapabilities(true)
    cla.setDebug(false)

    classifier.setClassifier(cla)
    generate_model(classifier, patterns)
  }

  protected def test_subclass(classifier: Classifier) = classifier match {
    case cla: Bagging => cla
    case x => throw new Exception(this + s" requires BagNB. not ${x.getClass}")
  }
}

