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
import ml.models.{ChuteModel, Model}
import weka.classifiers.Classifier
import weka.classifiers.rules.ZeroR

case class Chute(seed: Int = 42) extends Learner {
  override val toString = s"chu"
  val boundaryType = "nenhuma"
  val attPref = "ambos"
  val id = 13133166
  val abr = toString

  def expected_change(model: Model)(pattern: Pattern): Double = ???

  def build(patterns: Seq[Pattern]): Model = ChuteModel(seed)

  def update(model: Model, fast_mutable: Boolean, semcrescer: Boolean)(pattern: Pattern) = ???
}
