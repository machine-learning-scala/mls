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
import weka.classifiers.meta.AdaBoostM1
import weka.classifiers.trees.RandomTree

case class ABoo(seed: Int = 42, iterations: Int = 10) extends BatchWekaLearner {
  override val toString = s"ABoo" + (if (iterations != 10) iterations else "")
  val boundaryType = "flexível"
  val attPref = "ambos"
  val id = 5542
  val abr = toString

  def expected_change(model: Model)(pattern: Pattern): Double = ???

  def build(patterns: Seq[Pattern]): Model = {
    val classifier = new AdaBoostM1
    classifier.setSeed(seed)
    classifier.setNumIterations(iterations)
    classifier.setDebug(false)
    classifier.setDoNotCheckCapabilities(true)
    val rndtree = new RandomTree
    rndtree.setDebug(false)
    rndtree.setDoNotCheckCapabilities(true)
    classifier.setClassifier(rndtree)
    //    classifier.setUseResampling()
    //    classifier.setWeightThreshold()
    generate_model(classifier, patterns) //.padTo(3, patterns.head))
  }

  protected def test_subclass(classifier: Classifier) = classifier match {
    case cla: ABoo => cla
    case x => throw new Exception(this + s" requires ABoo. not ${x.getClass}")
  }
}

