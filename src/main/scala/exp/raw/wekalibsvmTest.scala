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

import ml.Pattern
import ml.classifiers.{SVMLibRBF, SVMLibDegree1}
import util.Datasets
import weka.filters.Filter

import scala.util.Random

object wekalibsvmTest extends App with FilterTrait {
   val d = Random.shuffle(Datasets.arff("/home/davi/wcs/ucipp/uci/leaf.arff").right.get)
   val tr = d.take(d.size/2)
   val ts = d.drop(d.size/2)
   //  val tr = Datasets.patterns2instances(d.take(1000))
   //  val ts = Datasets.patterns2instances(d.drop(1000))
   //  val svm = new LibSVM()
   //  svm.setDoNotReplaceMissingValues(true)
   //  svm.setNormalize(false)
   //  svm.setProbabilityEstimates(true)
   //  svm.setDoNotCheckCapabilities(true)
   //  svm.setDebug(false)
   //  svm.setSeed(0)
   //  Tempo.start
   //  svm.buildClassifier(tr)
   //  Tempo.print_stop
   //  print(svm.distributionForInstance(ts.firstInstance()).toList)

   val svm = SVMLibRBF()
   val m = svm.build(tr)

   val (fpool, binaf, zscof) = criaFiltro(tr, 0)
   val ftestSet = aplicaFiltro(ts, 0, binaf, zscof)
   val svmf = SVMLibDegree1()
   val mf = svm.build(fpool)
   println(m.accuracy(ts))
   println(mf.accuracy(ftestSet))


   //  svm.setSVMType(new SelectedTag(LibSVM.SVMTYPE_C_SVC,    LibSVM.TAGS_SVMTYPE))
   //  d.take(50) foreach (Datasets.pattern2TrainingSample _ andThen svm.train)

}

trait FilterTrait {
   def criaFiltro(tr: Seq[Pattern], fold: Int) = {
      //bina
      val binaf = Datasets.binarizeFilter(tr)
      val binarizedTr = Datasets.applyFilter(binaf)(tr)

      //tr
      val zscof = Datasets.zscoreFilter(binarizedTr)
      val pool = {
         val filteredTr = Datasets.applyFilter(zscof)(binarizedTr)
         new Random(fold).shuffle(filteredTr.sortBy(_.id))
      }

      (pool, binaf, zscof)
   }

   def aplicaFiltro(ts: Seq[Pattern], fold: Int, binaf: Filter, zscof: Filter) = {
      //ts
      val binarizedTs = Datasets.applyFilter(binaf)(ts)
      val testSet = {
         val filteredTs = Datasets.applyFilter(zscof)(binarizedTs)
         new Random(fold).shuffle(filteredTs.sortBy(_.id))
      }
      testSet
   }
}
