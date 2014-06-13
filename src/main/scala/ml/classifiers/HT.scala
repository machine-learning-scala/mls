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
import weka.classifiers.trees.HoeffdingTree
import ml.models.Model
import weka.classifiers.Classifier

case class HT() extends BatchWekaLearner {
  override val toString = "HT"

  def expected_change(model: Model)(pattern: Pattern): Double = ???

  def build(patterns: Seq[Pattern]): Model = {
    val classifier = new HoeffdingTree
    generate_model(classifier, patterns)
  }

  protected def test_subclass(cla: Classifier) = cla match {
    case n: HoeffdingTree => n
    case _ => throw new Exception(this + " requires HoeffdingTree.")
  }

  override def EMC(model: Model)(patterns: Seq[Pattern]): Pattern = ???
}
