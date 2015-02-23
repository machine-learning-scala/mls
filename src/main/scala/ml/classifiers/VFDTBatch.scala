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
import weka.classifiers.trees.HoeffdingTree

/**
 * fixed GraceTime = nclasses * 5
 */
case class VFDTBatch() extends BatchWekaLearner {
   override val toString = s"VFDTBatch"
   val boundaryType = "rígida"
   val attPref = "ambos"
   val id = -6
   val abr = toString

   def expected_change(model: Model)(pattern: Pattern): Double = ???

   def build(patterns: Seq[Pattern]): Model = {
      val classifier = new HoeffdingTree
      classifier.setGracePeriod(math.min(200, patterns.head.nclasses * 5))
      generate_model(classifier, patterns)
   }

   protected def test_subclass(cla: Classifier) = cla match {
      case n: HoeffdingTree => n
      case _ => throw new Exception(this + " requires HoeffdingTree.")
   }
}
