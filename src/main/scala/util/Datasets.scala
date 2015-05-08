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
package util

import java.io.{BufferedReader, FileReader, IOException}

import fr.lip6.jkernelmachines.`type`.TrainingSample
import ml.{Pattern, PatternParent}
import weka.core.Instances
import weka.core.converters.ArffLoader.ArffReader
import weka.filters.Filter
import weka.filters.unsupervised.attribute._

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.util.{Random, Failure, Success, Try}

object Datasets extends Lock {

   import scala.collection.JavaConversions._

   val predAttsLimit = 998

   /**
    * Reads an ARFF file.
    * Remove useless attributes.
    */
   def arff(arq: String, dedup: Boolean = true) = {
      try {

         //Extract instances from file and close it.
         val reader = new BufferedReader(new FileReader(arq))
         val instances = new ArffReader(reader).getData
         reader.close()
         instances.setClassIndex(instances.numAttributes() - 1)
         instances.setRelationName(arq)

         //Removes useless atts.
         val instancesUselessRemoved = rmUselessWeka(instances)
         if (instances.numAttributes() != instancesUselessRemoved.numAttributes()) println(s"${instances.numAttributes() - instancesUselessRemoved.numAttributes()} useless attributes removed from $arq.")

         //Random projection of atts. (está roubando um pouco aqui, mas é necessário para que o SQLite aceite o dataset.
         //Há um limite de 1998 atributos; como já estou interferindo vou reduzir para 998 (pois o mysql tem limite de 1000), pois com 1998 estava muito lento.
         //Note-se que a projeção resulta em atributos numéricos.
         val projected = rndProjectionWeka(instancesUselessRemoved, arq)
         if (instancesUselessRemoved.numAttributes() != projected.numAttributes()) println(s"random projection applied to shrink ${instancesUselessRemoved.numAttributes()} ${projected.numAttributes()} attributes in $arq.")

         //Assigns ids from zero. (should be one of the first things because of weka filters' indeterminism)
         val parent = PatternParent(projected)
         val idpatts = projected.zipWithIndex.map { case (instance, idx) => Pattern(idx, instance, missed = false, parent)}

         //Deduplication.
         println("Only distinct instances will be kept, but preserving ARFF original line number as id, starting from zero.")
         val distinctPatts = if (dedup) distinctMode(idpatts) else idpatts

         //Alerts about how many duplicates were found.
         if (instances.numInstances() != distinctPatts.size) {
            println("In dataset " + arq + ": " + (instances.numInstances() - distinctPatts.size) + " duplicate instances eliminated! Distinct = " + distinctPatts.size + " original:" + instances.numInstances())
         }

         Right(distinctPatts.toVector)
      } catch {
         case ex: IOException => Left("Problems reading file " + arq + ": " + ex.getMessage)
      }
   }

   /**
    * Deduplication keeping only the pattern with the mode of the involved labels.
    * @param patts
    * @return
    */
   def distinctMode(patts: mutable.Buffer[Pattern]) = patts groupBy (x => x) map {
      case (keyPatt: Pattern, ArrayBuffer(patt: Pattern)) => patt
      case (keyPatt: Pattern, listPatt: ArrayBuffer[Pattern]) =>
         val hist = Array.fill(keyPatt.nclasses)(0)
         listPatt foreach (p => hist(p.label.toInt) += 1)
         val modeLabel = hist.zipWithIndex.max._2
         keyPatt.relabeled_reweighted(modeLabel, keyPatt.weight, new_missed = false)
   }

   def LOO[T, U](patterns: Array[U], parallel: Boolean = false)(f: (Seq[U], U) => T): Seq[T] = {
      if (parallel) ???
      var i = 0
      val n = patterns.size
      val array = patterns
      val list = patterns.toList
      val q = mutable.Queue[T]()
      while (i < n) {
         q += f(list.take(i) ++ list.drop(i + 1), array(i))
         i += 1
      }
      q.toList
   }

   def LTO[T, U](patterns: Array[U], parallel: Boolean = false)(f: (Seq[U], Seq[U]) => T): Seq[T] = {
      if (parallel) ???
      var i = 0
      val n = patterns.size
      val array = patterns
      val list = patterns.toList
      val q = mutable.Queue[T]()
      while (i < n - 1) {
         q += f(list.take(i) ++ list.drop(i + 2), Seq(array(i), array(i + 1)))
         i += 2
      }
      q.toList
   }

   def LThO[T, U](patterns: Array[U], parallel: Boolean = false)(f: (Seq[U], Seq[U]) => T): Seq[T] = {
      if (parallel) ???
      var i = 0
      val n = patterns.size
      val array = patterns
      val list = patterns.toList
      val q = mutable.Queue[T]()
      while (i < n - 2) {
         q += f(list.take(i) ++ list.drop(i + 3), Seq(array(i), array(i + 1), array(i + 2)))
         i += 3
      }
      q.toList
   }

   /**
    * @param patterns
    * @param k
    * @param parallel
    * @param f f(tr, ts, foldnr, minSize) => T
    * @return Seq[T]
    */
   def kfoldCV[T](patterns: => Vector[Pattern], k: Int = 10, parallel: Boolean = false)(f: (=> Vector[Pattern], => Vector[Pattern], Int, Int) => T) = {
      //    patterns.take(4) foreach (x => println(x.toStringCerto))
      lazy val folds = {
         val n = patterns.length
         val tmp = Array.fill(k)(Vector[Pattern]())
         val grouped = patterns.groupBy(_.label).values.flatten.toArray
         var i = 0
         while (i < n) {
            tmp(i % k) +:= grouped(i)
            i += 1
         }
         tmp.toVector
      }
      lazy val testfolds = folds
      lazy val trainfolds = for (fo <- 0 until k) yield folds.filterNot(_.sameElements(testfolds(fo))).flatten
      val minSize = trainfolds.map(_.length).min
      val seq = if (parallel) (0 until k).par else 0 until k
      seq.map { foldnr =>
         lazy val tr = trainfolds(foldnr)
         lazy val ts = testfolds(foldnr)
         f(tr, ts, foldnr, minSize)
      }
   }

   def kfoldCV2[T](patterns: => Seq[Vector[Pattern]], k: Int = 10, parallel: Boolean = false)(f: (Seq[Vector[Pattern]], Seq[Vector[Pattern]], Int, Int) => T) = {
      lazy val folds = {
         val n = patterns.length
         val tmp = Array.fill(k)(Seq[Vector[Pattern]]())
         def label(s: Vector[Pattern]) = s.groupBy(_.label).maxBy(_._2.size)._1 //Random.shuffle(s).head.label <-violates general contract
         val grouped = (patterns sortBy label).toArray
         var i = 0
         while (i < n) {
            tmp(i % k) +:= grouped(i)
            i += 1
         }
         tmp.toVector
      }.toSeq
      lazy val testfolds = folds
      lazy val trainfolds = for (fo <- 0 until k) yield folds.filterNot(_.sameElements(testfolds(fo))).flatten
      val minSize = trainfolds.map(_.length).min
      val seq = if (parallel) (0 until k).par else 0 until k
      seq.map { foldnr =>
         lazy val tr = trainfolds(foldnr)
         lazy val ts = testfolds(foldnr)
         f(tr, ts, foldnr, minSize)
      }
   }

   def zscoreFilter(patts: Seq[Pattern]) = {
      val stand_filter = new Standardize
      stand_filter.setInputFormat(patts.head.dataset())
      stand_filter
   }

   def binarizeFilter(patts: Seq[Pattern]) = {
      val bina_filter = new NominalToBinary
      bina_filter.setInputFormat(patts.head.dataset())
      bina_filter
   }

   /**
    * Create a new Instances that contains Pattern objects instead of Instance objects.
    * @param patterns
    * @return
    */
   def patterns2instances(patterns: Seq[Pattern]) = if (patterns.isEmpty) throw new Error("Empty sequence of patterns; cannot generate Weka Instances object.")
   else {
      val new_instances = new Instances(patterns.head.dataset, 0, 0)
      patterns foreach new_instances.add
      new_instances
   }

   /**
    * Warning: nondeterministic process regarding patterns resulting order.
    * @param patts
    * @param filter
    * @return
    */
   def applyFilter(filter: Filter)(patts: Seq[Pattern]) = if (patts.isEmpty) Vector()
   else {
      val instances = patterns2instances(patts)
      instances.zip(patts).foreach { case (ins, pat) =>
         if (ins.weight() != 1) throw new Error(s"Weight ${ins.weight()} differs from 1! That info would be lost.")
         ins.setWeight(pat.id)
      }
      val newInstances = Filter.useFilter(instances, filter) //Weka Filter clones every instance.
      val res = newInstances.map { case instance => Pattern(instance.weight().toInt, instance, missed = false, PatternParent(instance.dataset()))}
      instances.foreach(_.setWeight(1))
      res.foreach(_.setWeight(1))
      res.toVector
   }

   //Weka   -----------------------------------
   def rndProjectionWeka(instances2: Instances, arq: String) = if (instances2.numAttributes > predAttsLimit) {
      val filter = new RandomProjection
      filter.setNumberOfAttributes(predAttsLimit)
      filter.setInputFormat(instances2)
      filter.setSeed(0)
      Try(Filter.useFilter(instances2, filter)) match {
         case Success(x) =>
            println("Reduced attributes from " + instances2.numAttributes + " to " + x.numAttributes + " in '" + arq + "'.")
            x
         case Failure(ex) => justQuit("\nSkipping dataset '" + arq + "' due to " + ex + "\n" + Thread.currentThread().getStackTrace.mkString("\n"))
      }
   } else instances2

   def rmUselessWeka(instances: Instances) = {
      val rmUseless_filter = new RemoveUseless
      //    rmUseless_filter.setMaximumVariancePercentageAllowed()
      rmUseless_filter.setInputFormat(instances)
      Filter.useFilter(instances, rmUseless_filter)
   }

   def normalizeWeka(instances: Instances, scale: Double = 1, translation: Double = 0) = {
      val norm_filter = new Normalize
      norm_filter.setInputFormat(instances)
      norm_filter.setScale(scale)
      norm_filter.setTranslation(translation)
      Filter.useFilter(instances, norm_filter)
   }

   def pcaWeka(ins: Instances, n: Int) = {
      val pc = new PrincipalComponents
      pc.setInputFormat(ins)
      pc.setMaximumAttributes(n)
      Filter.useFilter(ins, pc)
   }

   //JKernelMachines --------------------------
   /**
    * Create a list of TrSamps.
    * @param patterns
    * @return
    */
   def patterns2TrainingSamples(patterns: Seq[Pattern]) = if (patterns.isEmpty) {
      println("Empty sequence of patterns; cannot generate empty list of TrainingSamples.")
      throw new Error("Empty sequence of patterns; cannot generate list of TrainingSamples.")
   } else {
      patterns.toList map (p => new TrainingSample[Array[Double]](p.array, p.label.toInt))
   }

   def pattern2TrainingSample(pattern: Pattern) = new TrainingSample[Array[Double]](pattern.array, pattern.label.toInt)

   //useless ----------------------------------
   val readOnly: Boolean = true
   var fileLocked: Boolean = false

   def isOpen() = ???

   def close() = ???
}
