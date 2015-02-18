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

case class NBBatch() extends BatchWekaLearner {
   override val toString = s"NBBatch"
   val boundaryType = "flexível"
   val attPref = "nominal"
   val id = 12
   val abr = toString

   def expected_change(model: Model)(pattern: Pattern): Double = ???

   def build(patterns: Seq[Pattern]): Model = {
      val classifier = new NaiveBayes
      classifier.setUseSupervisedDiscretization(true) //true=slow?
      classifier.setDoNotCheckCapabilities(true)
      generate_model(classifier, patterns)
   }

   protected def test_subclass(classifier: Classifier) = classifier match {
      case cla: NaiveBayes => cla
      case _ => throw new Exception(this + " requires NaiveBayes.")
   }

   override def EMC(model: Model)(patterns: Seq[Pattern]): Pattern = ???
}
