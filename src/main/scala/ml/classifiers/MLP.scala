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
import weka.classifiers.meta.RotationForest
import weka.classifiers.trees.RandomTree

case class MLP(seed: Int = 42) extends BatchWekaLearner {
  override val toString = s"MLP"
  val boundaryType = "flexível"
  val attPref = "numérico"
  val id = 554110
  val abr = toString

  def expected_change(model: Model)(pattern: Pattern): Double = ???

  def build(patterns: Seq[Pattern]): Model = {
    val classifier = new weka.classifiers.functions.MultilayerPerceptron

    classifier.setSeed(seed)
    classifier.setDebug(false)
    classifier.setDoNotCheckCapabilities(true)
    val rndtree = new RandomTree
    rndtree.setDebug(false)
    rndtree.setDoNotCheckCapabilities(true)
    generate_model(classifier, patterns) //.padTo(3, patterns.head))
  }

  protected def test_subclass(classifier: Classifier) = classifier match {
    case cla: weka.classifiers.functions.MultilayerPerceptron => cla
    case x => throw new Exception(this + s" requires MLP. not ${x.getClass}")
  }
}

