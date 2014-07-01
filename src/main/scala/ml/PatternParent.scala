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
package ml

import util.Datasets
import weka.core.Instance

import scala.collection.JavaConversions._

/**
 * Probably only for graphical ends.
 * @param dataset
 */
case class PatternParent(dataset: weka.core.Instances) {

  case class Point(p: Instance) {
    val x = p.value(0)
    val y = p.value(1)
  }

  lazy val instancesPCA = Datasets.normalize(if (dataset.numClasses < 3) dataset else Datasets.pca(dataset, 2), 2, -1)
  lazy val instancesPCAmap = dataset.map(_.toDoubleArray.dropRight(1).toSeq).zip(instancesPCA map Point).toMap

  def xy(pa: Pattern) = instancesPCAmap.get(pa.vector) match {
    case Some(point) => point
    case None => println("PCA coordinate search failed! Perhaps the pattern " + pa + " was not in the pool provided to the strategy.")
      sys.exit(1)
  }
}
