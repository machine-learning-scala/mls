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

import fr.lip6.jkernelmachines.classifier.LaSVM
import fr.lip6.jkernelmachines.evaluation.ApEvaluator
import fr.lip6.jkernelmachines.io.LibSVMImporter
import fr.lip6.jkernelmachines.kernel.typed.DoubleGaussL2
import fr.lip6.jkernelmachines.`type`.TrainingSample
import util.Datasets

import scala.util.Random

object jkernmachsTest extends App {
  //setting kernel
  val kernel = new DoubleGaussL2()
  kernel.setGamma(0.005)

  //setting SVM parameters
  val svm = new LaSVM(kernel)
  svm.setC(1000) //C hyperparameter
  svm.setE(1)
  //epochs, 1: online
  //svm.setB() //bias, default em zero, mas no artigo diz que é definido automaticamente
  // tau = 0.001

  val d = Random.shuffle(Datasets.patternsFromSQLite("/home/davi/wcs/ucipp/uci")("iris").value)
  d.take(50) foreach (Datasets.pattern2TrainingSample _ andThen svm.train)
  //  svm.

}
