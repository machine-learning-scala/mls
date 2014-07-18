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

object jkernmachsTest extends App {
  //setting kernel
  val kernel = new DoubleGaussL2()
  kernel.setGamma(2.0)

  //setting SVM parameters
  val svm = new LaSVM(kernel)
  svm.setC(10) //C hyperparameter
  //  svm.train()
}
