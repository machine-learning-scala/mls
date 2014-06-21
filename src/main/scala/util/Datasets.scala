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

import ml.{Pattern, PatternParent}
import weka.core.Instances
import weka.core.converters.ArffLoader.ArffReader
import weka.experiment.InstanceQuery
import weka.filters.Filter
import weka.filters.unsupervised.attribute._

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object Datasets {

  import scala.collection.JavaConversions._

  def distinctMode(patts: mutable.Buffer[Pattern]) =
    patts groupBy (x => x) map {
      case (keyPatt: Pattern, ArrayBuffer(patt: Pattern)) => patt
      case (keyPatt: Pattern, listPatt: ArrayBuffer[Pattern]) =>
        val hist = Array.fill(keyPatt.nclasses)(0)
        listPatt foreach (p => hist(p.label.toInt) += 1)
        val modeLabel = hist.zipWithIndex.max._2
        keyPatt.relabeled_reweighted(modeLabel, keyPatt.weight, new_missed = false)
    }

  def rmUseless(instances: Instances) = {
    val rmUseless_filter = new RemoveUseless
    //    stand_filter.setMaximumVariancePercentageAllowed()
    rmUseless_filter.setInputFormat(instances)
    Filter.useFilter(instances, rmUseless_filter)
  }

  /**
   * Reads an ARFF file.
   * Duplicate instances will be removed, only the mode label will be kept.
   *
   * TODO: read the file like a stream (poker dataset was terribly slow to load, it seems to be ok now :S)
   * @return (processed patterns, arff-header, processed Instances object still with duplicates)
   */
  def arff(bina: Boolean, limit: Int = -1, debug: Boolean = true)(arq: String) = {
    try {
      //Extract instances from file and close it.
      val reader = new BufferedReader(new FileReader(arq))
      val instances0 = if (limit == -1) new ArffReader(reader).getData else new Instances(new ArffReader(reader).getData, 0, limit)
      //      println("ARFF read.")
      instances0.setClassIndex(instances0.numAttributes() - 1)
      instances0.setRelationName(arq)
      val instances1 = if (bina) {
        println("z-score will be applied and useless attributes removed...")
        val res = zscore(binarize(rmUseless(instances0)))
        if (debug) println(arq + " binarized.")
        res
      } else rmUseless(instances0)
      val instances = instances1
      if (debug) println("Useless atts removed from " + arq + ".")
      reader.close()

      val arff_header = instances.toString.split("\n").takeWhile(!_.contains("@data")).toList ++ List("@data\n")
      val parent = PatternParent(instances)
      val patterns = instances.zipWithIndex.map { case (instance, idx) => Pattern(idx + 1, instance, false, parent)} //zero is not a valid Pattern id
      val distinct = distinctMode(patterns)
      if (instances.numInstances() != distinct.size) {
        if (debug) println("In dataset " + arq + ": duplicate instances eliminated! Distinct = " + distinct.size + " original:" + instances.numInstances())
      }
      Right(distinct.toStream)
    } catch {
      case ex: IOException => Left("Problems reading file " + arq + ": " + ex.getMessage)
    }
  }

  /**
   * Create a new Instances that contains Pattern objects instead of Instance objects.
   * @param patterns
   * @return
   */
  def patterns2instances(patterns: Seq[Pattern]) = if (patterns.isEmpty) {
    println("Empty sequence of patterns; cannot generate Weka Instances object.")
    sys.exit(0)
  } else {
    val new_instances = new Instances(patterns.head.dataset, 0, 0)
    patterns foreach new_instances.add
    new_instances
  }

  def kfoldCV[T](patterns: Seq[Pattern], k: Int = 10, parallel: Boolean = false)(f: (Seq[Pattern], Seq[Pattern], Int, Int) => T): Seq[T] = {
    val n = patterns.length
    val folds = Array.fill(k)(Seq[Pattern]())
    val grouped = patterns.groupBy(_.label).values.flatten.toArray
    var i = 0
    while (i < n) {
      folds(i % k) +:= grouped(i)
      i += 1
    }
    val testfolds = folds
    val trainfolds = for (fo <- 0 until k) yield folds.filterNot(_.sameElements(testfolds(fo))).flatten
    val minSize = trainfolds.map(_.length).min
    val seq = if (parallel) (0 until k).par else 0 until k
    seq.map(foldnr => f(trainfolds(foldnr), testfolds(foldnr), foldnr, minSize)).toList
  }

  def binarize(instances: Instances) = {
    val bina_filter = new NominalToBinary
    bina_filter.setInputFormat(instances)
    Filter.useFilter(instances, bina_filter)
  }

  def normalize(instances: Instances, scale: Double = 1, translation: Double = 0) = {
    val norm_filter = new Normalize
    norm_filter.setInputFormat(instances)
    norm_filter.setScale(scale)
    norm_filter.setTranslation(translation)
    Filter.useFilter(instances, norm_filter)
  }

  def zscore(instances: Instances) = {
    val stand_filter = new Standardize
    stand_filter.setInputFormat(instances)
    Filter.useFilter(instances, stand_filter)
  }

  def pca(ins: Instances, n: Int) = {
    val pc = new PrincipalComponents
    pc.setInputFormat(ins)
    pc.setMaximumAttributes(n)
    Filter.useFilter(ins, pc)
  }

  /**
   * Reads a SQLite dataset.
   * Assigns the rowid to pattern id.
   * Assumes there is no duplicates.
   */
  def patternsFromSQLite(path: String)(dataset: String) = {
    val arq = path + dataset + ".db"
    try {
      val query = new InstanceQuery()
      query.setDatabaseURL("jdbc:sqlite:////" + arq)
      query.setQuery("select * from inst order by rowid")
      query.setDebug(false)
      val instances = query.retrieveInstances()
      instances.setClassIndex(instances.numAttributes() - 1)
      instances.setRelationName(dataset)
      val parent = PatternParent(instances)
      val patterns = instances.zipWithIndex.map { case (instance, idx) => Pattern(idx + 1, instance, false, parent)} //rowid is always > 0
      query.close()
      Right(patterns.toStream)
    } catch {
      case ex: IOException => Left("Problems reading file " + arq + ": " + ex.getMessage)
    }
  }
}