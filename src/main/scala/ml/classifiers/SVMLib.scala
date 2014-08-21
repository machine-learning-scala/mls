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
import ml.models.{Model, WekaBatModel}
import util.Datasets
import weka.classifiers.Classifier
import weka.classifiers.functions.LibSVM

/**
 * SVM conventional (batch training)
 * Usa LibSVM wrapper for Weka.
 */
case class SVMLib(seed: Int = 42, notes: String = "") extends BatchWekaLearner {
  override val toString = "SVM"
  val originalStream = System.out
  val dummyStream = new PrintStream(new OutputStream() {
    def write(b: Int) {}
  })

  def expected_change(model: Model)(pattern: Pattern) = ???

  def EMC(model: Model)(patterns: Seq[Pattern]) = ???

  def build(pool: Seq[Pattern]) = {
    val classifier = new LibSVM()
    classifier.setDoNotCheckCapabilities(true)
    classifier.setOptions(weka.core.Utils.splitOptions("-J"))
    classifier.setOptions(weka.core.Utils.splitOptions("-V"))
    classifier.setSeed(seed)
    classifier.setDebug(false)
    classifier.setProbabilityEstimates(true)
    generate_model(classifier, pool)
  }

  override protected def generate_model(classifier: Classifier, patterns: Seq[Pattern]) = {
    System.setOut(dummyStream)
    classifier.buildClassifier(Datasets.patterns2instances(patterns))
    System.setOut(originalStream)
    WekaBatModel(classifier, patterns)
  }

  protected def test_subclass(classifier: Classifier) = classifier match {
    case cla: LibSVM => cla
    case _ => throw new Exception(this + " requires LibSVM.")
  }
}

object TestSVM extends App {
  val d = Datasets.arff(bina = true)("/home/davi/wcs/ucipp/uci/abalone-11class.arff", zscored = false).right.get.toList.take(1000)
  val f = Datasets.zscoreFilter(d)
  val df = Datasets.applyFilterChangingOrder(d, f)
  lazy val l = SVMLib()

  var m = l.build(df.take(df.head.nclasses))
  df.drop(df.head.nclasses).foreach { p => m = l.update(m, fast_mutable = true)(p)
    println(m.accuracy(df))
  }
}