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
import ml.models.{WekaBatModel, Model}
import util.Datasets
import weka.classifiers.Classifier
import weka.classifiers.rules.JRip
import weka.classifiers.trees.{RandomForest2, RandomForest}

case class RF(seed: Int = 42, trees: Int = 10, depth: Int = 0) extends BatchWekaLearner {
   override val toString = s"RFw"
   val boundaryType = "flexível"
   val attPref = "ambos"
   val id = 773
   //   773 dep0 trees10
   //666773 dep10 trees15
   val abr = toString

   def expected_change(model: Model)(pattern: Pattern): Double = ???

   def build(patterns: Seq[Pattern]): Model = {
      val classifier = new RandomForest2
      classifier.setSeed(seed)
      classifier.setDontCalculateOutOfBagError(true)
      classifier.setDebug(false)
      classifier.setDoNotCheckCapabilities(true)
      classifier.setNumTrees(trees)
      classifier.setMaxDepth(depth)
      generate_model(classifier, patterns.padTo(3, patterns.head))
   }

   protected def test_subclass(classifier: Classifier) = classifier match {
      case cla: RandomForest2 => cla
      case _ => throw new Exception(this + " requires RandomForest.")
   }
}

object RFTest extends App {
   val ps = Datasets.arff("/home/davi/wcs/ucipp/uci/abalone-3class.arff").right.get
   val l = RF(42, 10)
   val m = l.build(ps.tail)
   ps foreach (x => println(" " + m.JS(x) + " "))
}