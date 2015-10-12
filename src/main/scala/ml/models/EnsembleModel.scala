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

case class EnsembleModel(models: Seq[Model]) extends Model {
  def JS(pattern: Pattern) = ???

  def predict(instance: Pattern) = models.map(_.predict(instance)).groupBy(identity).maxBy(_._2.size)._1

  //  output(instance).zipWithIndex.maxBy(_._1)._2

  def distribution(instance: Pattern) = models.map(_.distribution(instance)).reduce((a, b) => a.zip(b).map { case (x, y) => x + y }.map(_ / models.size))

  def output(instance: Pattern) = models.map(_.output(instance)).reduce((a, b) => a.zip(b).map { case (x, y) => x + y })
}
