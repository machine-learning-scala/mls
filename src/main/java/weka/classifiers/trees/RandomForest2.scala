package weka.classifiers.trees

import ml.Pattern
import weka.classifiers.meta.Bagging
import weka.core.{Utils, Instances}

class Bagging2 extends Bagging {
   def models = m_Classifiers
}

/*
elm-scala: an implementation of ELM in Scala using MTJ
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
class RandomForest2(minObjsAtLeaf: Int = 1) extends RandomForest0 with JSMeasure {
   def JS(p: Pattern) = JSdivergence(m_bagger.models.map(_.distributionForInstance(p)))

   /**
    * Builds a classifier for a set of instances.
    *
    * @param data the instances to train the classifier with
    * @throws Exception if something goes wrong
    */
   override def buildClassifier(data0: Instances) {

      // can classifier handle the data?
      getCapabilities.testWithFail(data0)

      // remove instances with missing class
      val data = new Instances(data0)
      data.deleteWithMissingClass()

      m_bagger = new Bagging2()
      val rTree = new RandomTree()

      // set up the random tree options
      m_KValue = m_numFeatures
      if (m_KValue < 1) m_KValue = (Utils.log2(data.numAttributes()) + 1).toInt
      rTree.setKValue(m_KValue)
      rTree.setMaxDepth(getMaxDepth)
      rTree.setMinNum(minObjsAtLeaf) //minObjsAtLeaf>1 to allow prob estimation for JS measure

      // set up the bagger and build the forest
      m_bagger.setClassifier(rTree)
      m_bagger.setSeed(m_randomSeed)
      m_bagger.setNumIterations(m_numTrees)
      m_bagger.setCalcOutOfBag(true)
      m_bagger.setNumExecutionSlots(m_numExecutionSlots)
      m_bagger.buildClassifier(data)
   }
}
