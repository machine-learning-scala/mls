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

import exp.raw.FilterTrait
import ml.Pattern
import ml.models.{Model, WekaBatModel}
import util.{Datasets, Tempo}
import weka.classifiers.Classifier
import weka.classifiers.functions.LibLINEAR
import weka.core.SelectedTag

import scala.util.Random

case class SVMLinear(seed: Int = 42) extends BatchWekaLearner {
   override val toString = "SVML"
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
      val classifier = new LibLINEAR()
      //      classifier.setBias()
      classifier.setConvertNominalToBinary(false)
      //      classifier.setCost()
      classifier.setDoNotReplaceMissingValues(true)
      //      classifier.setEps()
      classifier.setNormalize(false)
      classifier.setProbabilityEstimates(true)
      classifier.setSVMType(new SelectedTag(0, LibLINEAR.TAGS_SVMTYPE))
      //      classifier.setWeights()
      classifier.setDebug(false)
      classifier.setDoNotCheckCapabilities(true)
      generate_model(classifier, pool)
   }

   override protected def generate_model(classifier: Classifier, patterns: Seq[Pattern]) = {
      classifier.buildClassifier(Datasets.patterns2instances(patterns))
      WekaBatModel(classifier, patterns)
   }

   protected def test_subclass(classifier: Classifier) = classifier match {
      case cla: LibLINEAR => cla
      case _ => throw new Exception(this + " requires LibLINEAR.")
   }
}

object SVMLinearTest extends App with FilterTrait {
   val (tr, ts) = Random.shuffle(Datasets.arff("/home/davi/wcs/ucipp/uci/abalone-11class.arff").right.get).splitAt(300)
   val (ftr, bf, zf) = criaFiltro(tr, 0)
   val fts = aplicaFiltro(ts, 0, bf, zf)
   val (ma, mb, mc) = (SVMLinear(42).build(ftr), SVMLib(42).build(ftr), SVMLibK(42).build(ftr))
   val a = Tempo.timev(ma.accuracy(fts))
   val b = Tempo.timev(mb.accuracy(fts))
   val c = Tempo.timev(mc.accuracy(fts))
   println(s"$a $b $c")
   println(ma.distribution(fts.head).toList)
   println(mb.distribution(fts.head).toList)
   println(mc.distribution(fts.head).toList)

}