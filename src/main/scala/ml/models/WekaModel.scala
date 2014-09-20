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
package ml.models

import ml.Pattern
import weka.classifiers.{Classifier, UpdateableClassifier}

class WekaModel(var classifier: Classifier) extends Model {
  val L = -1

  def output(instance: Pattern) = distribution(instance)

  def distribution(pattern: Pattern) =
    if (classifier == null) throw new Exception("Underlying model already changed! Please call update with fast_mutable disabled.")
    else classifier.distributionForInstance(pattern)

  def predict(instance: Pattern) =
    if (classifier == null) throw new Exception("Underlying model already changed! Please call update with fast_mutable disabled.")
    else {
      //            println(instance + " <<<<<<<<<<<<<")
      classifier.classifyInstance(instance)
    }
}

case class WekaBatModel(private val batch_classifier: Classifier, training_set: Seq[Pattern]) extends WekaModel(batch_classifier)

case class WekaIncModel(private val incremental_classifier: Classifier with UpdateableClassifier, N: Int) extends WekaModel(incremental_classifier)