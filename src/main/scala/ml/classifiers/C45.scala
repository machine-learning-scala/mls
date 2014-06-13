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

import weka.classifiers.Classifier
import weka.classifiers.trees.J48
import ml.models.Model
import ml.Pattern

case class C45(min_leaf_size: Int=2) extends BatchWekaLearner {
  override val toString = "C45_" + min_leaf_size

  //  private def complexity(classifier: Classifier) = classifier match {
  //    //      case sgd: SGD =>
  //    case rf: RF => rf.toString.lines.filter(_.contains("Size of the tree")).map(_.split(':').last.toDouble).sum
  //    case j48: J48 => j48.measureNumLeaves * j48.measureTreeSize
  //    case jrip: JRip => val output = jrip.toString.lines.dropWhile(!_.contains("JRIP rules")).drop(3).toList
  //      lazy val nrules = output.filter(_.contains("Number of Rules")).toList.headOption match {
  //        case Some(txt) => txt.split(':').last.toDouble
  //        case None => println("Number of rules not found!")
  //          sys.exit(1)
  //      }
  //      val ands = output.takeWhile(_.contains("=")).map(_.dropRight(1)).mkString.split(')').length.toDouble
  //      ands
  //  }
  //
  // private def expected_change(classifier0: Classifier, instances: Instances)(pa: Pattern) = {
  //    val old_complexity = complexity(classifier0)
  //    val classifier = AbstractClassifier.makeCopy(classifier0) //to avoid changing state of original classifier
  //    ((0 until pa.nclasses).zip(classifier.distributionForInstance(pa)) map {
  //      case (c, p) =>
  //        val new_pa = pa.relabeled_reweighted(c, 1, new_missed = false)
  //        instances.add(new_pa)
  //        classifier.buildClassifier(instances)
  //        instances.remove(instances.numInstances() - 1)
  //        p * (complexity(classifier) - old_complexity).abs
  //    }).sum
  //  }
  def expected_change(model: Model)(pattern: Pattern): Double = ???

  def build(patterns: Seq[Pattern]): Model = {
    val classifier = new J48
    classifier.setMinNumObj(min_leaf_size)
    generate_model(classifier, patterns)
  }

  protected def test_subclass(classifier: Classifier) = classifier match {
    case cla: J48 => cla
    case _ => throw new Exception(this + " requires J48.")
  }

  override def EMC(model: Model)(patterns: Seq[Pattern]): Pattern = ???
}
