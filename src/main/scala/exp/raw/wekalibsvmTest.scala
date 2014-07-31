package exp.raw

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

import util.{Tempo, Datasets}
import weka.classifiers.functions.LibSVM
import weka.core.SelectedTag

import scala.util.Random

object wekalibsvmTest extends App {
  val d = Random.shuffle(Datasets.arff(true)("/home/davi/wcs/ucipp/uci/abalone-11class.arff").right.get)
  val tr = Datasets.patterns2instances(d.take(1000))
  val ts = Datasets.patterns2instances(d.drop(1000))
  val svm = new LibSVM()
  svm.setDoNotReplaceMissingValues(true)
  svm.setNormalize(false)
  svm.setProbabilityEstimates(true)
  svm.setDoNotCheckCapabilities(true)
  svm.setDebug(false)
  svm.setSeed(0)
  Tempo.start
  svm.buildClassifier(tr)
  Tempo.print_stop
  print(svm.distributionForInstance(ts.firstInstance()).toList)

  //  svm.setSVMType(new SelectedTag(LibSVM.SVMTYPE_C_SVC,    LibSVM.TAGS_SVMTYPE))
  //  d.take(50) foreach (Datasets.pattern2TrainingSample _ andThen svm.train)

}
