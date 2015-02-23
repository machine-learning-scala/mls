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
import weka.classifiers.trees.J48

case class C45(laplace: Boolean = true) extends BatchWekaLearner {
   override val toString = s"C4.5w"
   val id = 666003
   val abr = toString
   val boundaryType = "rígida"
   val attPref = "ambos"

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
   def expected_change(model: Model)(pattern: Pattern): Double = ???

   def build(patterns: Seq[Pattern]): Model = {
      val classifier = new J48
      classifier.setMinNumObj(math.min(10, patterns.head.nclasses * 2))
      classifier.setUseLaplace(laplace)
      classifier.setDoNotCheckCapabilities(true)
      generate_model(classifier, patterns)
   }

   protected def test_subclass(classifier: Classifier) = classifier match {
      case cla: J48 => cla
      case _ => throw new Exception(this + " requires J48.")
   }
}
