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

import java.io.{OutputStream, PrintStream}

import ml.Pattern
import ml.models.{WekaBatModel, Model}
import util.Datasets
import weka.classifiers.Classifier
import weka.classifiers.functions.LibSVM

case class SVMLinear(seed: Int = 42) extends BatchWekaLearner {
   override val toString = "SVM"
   val boundaryType = "flexível"
   val attPref = "numérico"
   val id = 556665
   val originalStream = System.out
   val dummyStream = new PrintStream(new OutputStream() {
      def write(b: Int) {}
   })
   val abr = toString

   def expected_change(model: Model)(pattern: Pattern) = ???

   def EMC(model: Model)(patterns: Seq[Pattern]) = ???

   def build(pool: Seq[Pattern]) = {
      val classifier = new LibSVM()
      classifier.setCacheSize(200) //MB
//      classifier.setCoef0()
//      classifier.setCost()
//      classifier.setDegree()
//      classifier.setDoNotReplaceMissingValues()
      classifier.setDoNotCheckCapabilities(true)
      classifier.setOptions(weka.core.Utils.splitOptions("-J")) //Turn off nominal to binary conversion.
      classifier.setOptions(weka.core.Utils.splitOptions("-V")) //Turn off missing value replacement.
      //      classifier.setOptions(weka.core.Utils.splitOptions("-Z")) //Turns on normalization of input data.
      classifier.setOptions(weka.core.Utils.splitOptions("-B")) //Generate probability estimates for classification.
      classifier.setSeed(seed)
      classifier.setDebug(false)
      classifier.setShrinking(true)
//      classifier.setKernelType()
      classifier.setProbabilityEstimates(true)
      generate_model(classifier, pool)
   }

   override protected def generate_model(classifier: Classifier, patterns: Seq[Pattern]) = {
      //    System.setOut(dummyStream)
      classifier.buildClassifier(Datasets.patterns2instances(patterns))
      //    System.setOut(originalStream)
      WekaBatModel(classifier, patterns)
   }

   protected def test_subclass(classifier: Classifier) = classifier match {
      case cla: LibSVM => cla
      case _ => throw new Exception(this + " requires LibSVM.")
   }

   //  override protected def next_classifier(wekamodel: WekaModel, fast_mutable: Boolean) = {
   //    val classifier = new LibSVM()
   //    classifier.setDoNotCheckCapabilities(true)
   //    classifier.setOptions(weka.core.Utils.splitOptions("-J"))
   //    classifier.setOptions(weka.core.Utils.splitOptions("-V"))
   //    classifier.setSeed(seed)
   //    classifier.setDebug(false)
   //    classifier.setProbabilityEstimates(true)
   //    classifier
   //  }
}
