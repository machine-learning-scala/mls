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
import weka.classifiers.Classifier


case class WekaBatModel2(batch_classifier: Classifier, labeled: Seq[Pattern], ftodos: Map[Int, Pattern])
  extends WekaModel(batch_classifier) {
  val precisouFiltro = !labeled.head.vector.sameElements(ftodos(labeled.head.id).vector)

  /**
   * verifica se trSet veio filtrado,
   * caso sim, filtra p.
   * Assume que é improvável o 1o elemento permanecer igual após filtro (é verificado em MetaLearner())
   * @param p
   * @return
   */
  def adequaFiltragem(p: Pattern) = if (precisouFiltro) ftodos(p.id) else p

  override def distribution(pattern: Pattern) =
    if (classifier == null) throw new Exception("Underlying model already changed! Please call update with fast_mutable disabled.")
    else classifier.distributionForInstance(adequaFiltragem(pattern))

  override def predict(pattern: Pattern) =
    if (classifier == null) throw new Exception("Underlying model already changed! Please call update with fast_mutable disabled.")
    else classifier.classifyInstance(adequaFiltragem(pattern))
}

