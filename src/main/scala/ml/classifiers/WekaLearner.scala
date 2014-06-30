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

import ml._
import weka.classifiers.{AbstractClassifier, UpdateableClassifier, Classifier}
import ml.models.{WekaIncModel, WekaBatModel, WekaModel, Model}
import util.Datasets

trait WekaLearner extends Learner{
  protected def test_subclass(classifier: Classifier): Classifier

  protected def next_classifier(wekamodel: WekaModel, fast_mutable: Boolean)= {
    val tested_classifier = test_subclass(wekamodel.classifier)
    val new_wc = if (fast_mutable) {
      wekamodel.classifier = null
      tested_classifier
    } else AbstractClassifier.makeCopy(tested_classifier)
    new_wc
  }

}

trait IncrementalWekaLearner extends WekaLearner {
  protected def cast2wekaincmodel(model: Model) = model match {
    case m: WekaIncModel => m
    case _ => throw new Exception("IncrementalWekaLearner requires WekaIncModel.")
  }

  protected def generate_model(classifier: Classifier with UpdateableClassifier, patterns: Seq[Pattern]) = {
    classifier.buildClassifier(Datasets.patterns2instances(patterns.take(patterns.head.nclasses)))//head.dataset())
    patterns foreach classifier.updateClassifier
    WekaIncModel(classifier)
  }

  def updateAll(model: Model, fast_mutable: Boolean = false)(patterns: Seq[Pattern]) = {
    val wekaincmodel = cast2wekaincmodel(model)
    val cla = next_classifier(wekaincmodel, fast_mutable).asInstanceOf[Classifier with UpdateableClassifier]
    patterns foreach cla.updateClassifier
    WekaIncModel(cla)
  }

  def update(model: Model, fast_mutable: Boolean = false)(pattern: Pattern) = {
    val wekaincmodel = cast2wekaincmodel(model)
    val cla = next_classifier(wekaincmodel, fast_mutable).asInstanceOf[Classifier with UpdateableClassifier]
    cla.updateClassifier(pattern)
    WekaIncModel(cla)
  }
}

trait BatchWekaLearner extends WekaLearner {
  protected def cast2wekabatmodel(model: Model) = model match {
    case m: WekaBatModel => m
    case _ => throw new Exception("BatchWekaLearner requires WekaModel.")
  }

  protected def generate_model(classifier: Classifier, patterns: Seq[Pattern]) = {
    classifier.buildClassifier(Datasets.patterns2instances(patterns))
    WekaBatModel(classifier, patterns)
  }

  def updateAll(model: Model, fast_mutable: Boolean = false)(patterns: Seq[Pattern]) = {
    val wekabatmodel = cast2wekabatmodel(model)
    val nb = next_classifier(wekabatmodel, fast_mutable)
    generate_model(nb, patterns ++ wekabatmodel.training_set)
  }

  def update(model: Model, fast_mutable: Boolean = false)(pattern: Pattern) = {
    val wekabatmodel = cast2wekabatmodel(model)
    val nb = next_classifier(wekabatmodel, fast_mutable)
    generate_model(nb, pattern +: wekabatmodel.training_set)
  }
}