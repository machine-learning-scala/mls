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

import no.uib.cipr.matrix.{DenseMatrix, DenseVector}
import weka.core._

/**
 * Two Patterns are equal if they have the same id.
 * (it could be the descriptive attributes (a vector) but id was chosen for performance)
 * zero is not a valid Pattern id
 */
object Pattern {
  def apply(id: Int, instance: Instance, missed: Boolean, parent: PatternParent) = new Pattern(id, instance, missed, parent)
}

case class Pattern(id: Int, vector: List[Double], label: Double, instance_weight: Double = 1, missed: Boolean = false, parent: PatternParent = null, weka: Boolean = false)
  extends DenseInstance(instance_weight, vector.toArray :+ label) {
  lazy val array = vector.toArray
  lazy val arraymtj = new DenseVector(array, false)
  lazy val arraymtjmatrix = {
    val m = new DenseMatrix(1, nattributes)
    var i = 0
    while (i < nattributes) {
      m.set(0, i, vector(i))
      i += 1
    }
    m
  }

  lazy val nclasses = if (parent == null) {
    println("Non weka instances! Assuming 3 classes!")
    3
  } else numClasses
  //  lazy val unlabel = Pattern(vector, -1, weight, missed, parent, weka)
  lazy val label_array = {
    val a = Array.fill(nclasses)(0d)
    a(label.toInt) = 1
    a
  }
  lazy val weighted_label_array = {
    val a = Array.fill(nclasses)(0d)
    //    if (label != -1)
    a(label.toInt) = weight
    a
  }
  lazy val weighted_label_list = {
    val idx = label.toInt
    val a = List.fill(idx)(0d) ++ List(weight) ++ List.fill(nclasses - idx)(0d)
    //    if (label != -1)
    a
  }
  lazy val reversed_weighted_label_array = {
    val a = Array.fill(nclasses)(weight)
    a(label.toInt) = 0d
    a
  }

  //  lazy val label_array = {
  //    val a = Array.fill(nclasses)(-1d)
  //    a(label.toInt) = 1
  //    a
  //  }
  //  lazy val weighted_label_array = {
  //    val a = Array.fill(nclasses)(-weight)
  //    if (label != -1) a(label.toInt) = weight
  //    a
  //  }
  //  lazy val reversed_weighted_label_array = {
  //    val a = Array.fill(nclasses)(weight)
  //    a(label.toInt) = -weight
  //    //    val a = Array.fill(nclasses)(weight)
  //    //    a(label.toInt) = 0d
  //    a
  //  }

  //  lazy val weighted_label_array_mtj = new DenseVector(weighted_label_array, false)
  //  lazy val reversed_weighted_label_array_mtj = new DenseVector(reversed_weighted_label_array, false)
  //  lazy val reversed_weighted_label_array_mtjmatrix = new DenseMatrix(reversed_weighted_label_array_mtj, false)

  //  lazy val weighted_label_array_brz = DenseVector(weighted_label_array)
  //  lazy val reversed_weighted_label_array_brz = DenseVector(reversed_weighted_label_array)

  lazy val nattributes = numAttributes - 1
  lazy val nnumeric = (for (i <- 0 until nattributes) yield attribute(i).isNumeric) count (_ == true)
  lazy val nnominal = (for (i <- 0 until nattributes) yield attribute(i).isNominal) count (_ == true)
  lazy val toStrWithMissed = toString + "%als:" + missed
  lazy val toString_without_class = (0 until numAttributes() - 1) map treat_nominal mkString ","
  lazy val x = parent.xy(this).x
  lazy val y = parent.xy(this).y
  if (weka) setDataset(parent.dataset)

  def this(id: Int, instance: Instance, missed: Boolean, parent: PatternParent) =
    this(id, instance.toDoubleArray.toList.dropRight(1), instance.classValue, instance.weight, missed, parent, true)

  override lazy val hashCode = vector.hashCode() //id cannot be used as hashCode because id is unique, and this would deteriorate hashMaps performance

  //  override def equals(that: Any) = that match {
  //    case that: Pattern => id == that.id //cannot use this because id is unique and we need to detect duplicates
  //    case _ => false
  //  }

  override def equals(that: Any) = that match {
    case that: Pattern => array.sameElements(that.array)
    case _ => false
  }


  /**
   * Create a copy with another label/weight/missed.
   */
  def relabeled_reweighted(new_label: Double, new_weight: Double, new_missed: Boolean) =
    Pattern(id, vector, new_label, new_weight, new_missed, parent, weka = true)

  private def treat_nominal(i: Int) = if (attribute(i).isNominal) attribute(i).value(value(i).toInt) else value(i)
}
