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

import java.io.{BufferedReader, File, FileReader, IOException}

import fr.lip6.jkernelmachines.`type`.TrainingSample
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

  /**
   * Reads an ARFF file.
   * Duplicate instances will be removed, only the mode label will be kept.
   *
   * TODO: read the file like a stream (poker dataset was terribly slow to load, it seems to be ok now :S)
   * @return (processed patterns, arff-header, processed Instances object still with duplicates)
   */
  def arff(bina: Boolean, limit: Int = -1, debug: Boolean = true)(arq: String, zscored: Boolean = true) = {
    try {
      //Extract instances from file and close it.
      val reader = new BufferedReader(new FileReader(arq))
      val instances0 = if (limit == -1) new ArffReader(reader).getData else new Instances(new ArffReader(reader).getData, 0, limit)
      //      println("ARFF read.")
      instances0.setClassIndex(instances0.numAttributes() - 1)
      instances0.setRelationName(arq)
      val instances = if (bina) {
        println("useless attributes will be removed...")
        val res = if (zscored) {
          println("z-score will be applied")
          zscore(binarize(rmUseless(instances0)))
        } else binarize(rmUseless(instances0))
        if (debug) println(arq + " binarized.")
        res
      } else rmUseless(instances0)
      if (debug) println("Useless atts removed from " + arq + ".")
      reader.close()

      lazy val arff_header = instances.toString.split("\n").takeWhile(!_.contains("@data")).toList ++ List("@data\n")
      val parent = PatternParent(instances)
      val patterns = instances.sortBy(_.toDoubleArray.toList.toString()).zipWithIndex.map { case (instance, idx) => Pattern(idx + 1, instance, false, parent)} //zero is not a valid Pattern id
      val distinct = distinctMode(patterns)
      if (instances.numInstances() != distinct.size) {
        println("In dataset " + arq + ": " + (instances.numInstances() - distinct.size) + " duplicate instances eliminated! Distinct = " + distinct.size + " original:" + instances.numInstances())
      }
      Right(distinct.toStream)
    } catch {
      case ex: IOException => Left("Problems reading file " + arq + ": " + ex.getMessage)
    }
  }

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

  def binarize(instances: Instances) = {
    val bina_filter = new NominalToBinary
    bina_filter.setInputFormat(instances)
    Filter.useFilter(instances, bina_filter)
  }

  def zscore(instances: Instances) = {
    val stand_filter = new Standardize
    stand_filter.setInputFormat(instances)
    Filter.useFilter(instances, stand_filter)
  }

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

  def LOO[T](patterns: Seq[Pattern], parallel: Boolean = false)(f: (Seq[Pattern], Pattern) => T): Seq[T] = {
    if (parallel) ???
    var i = 0
    val n = patterns.size
    val array = patterns.toArray
    val list = patterns.toList
    val q = mutable.Queue[T]()
    while (i < n) {
      q += f(list.take(i) ++ list.drop(i + 1), array(i))
      i += 1
    }
    q.toList
  }

  def kfoldCV[T](patterns: => Seq[Pattern], k: Int = 10, parallel: Boolean = false)(f: (=> Seq[Pattern], => Seq[Pattern], Int, Int) => T) {
    lazy val folds = {
      val n = patterns.length
      val tmp = Array.fill(k)(Seq[Pattern]())
      val grouped = patterns.groupBy(_.label).values.flatten.toArray
      var i = 0
      while (i < n) {
        tmp(i % k) +:= grouped(i)
        i += 1
      }
      tmp
    }
    lazy val testfolds = folds
    lazy val trainfolds = for (fo <- 0 until k) yield folds.filterNot(_.sameElements(testfolds(fo))).flatten
    val minSize = trainfolds.map(_.length).min
    val seq = if (parallel) (0 until k).par else 0 until k
    seq.foreach { foldnr =>
      lazy val tr = trainfolds(foldnr)
      lazy val ts = testfolds(foldnr)
      f(tr, ts, foldnr, minSize)
    }
  }

  def normalize(instances: Instances, scale: Double = 1, translation: Double = 0) = {
    val norm_filter = new Normalize
    norm_filter.setInputFormat(instances)
    norm_filter.setScale(scale)
    norm_filter.setTranslation(translation)
    Filter.useFilter(instances, norm_filter)
  }

  def zscoreFilter(patts: Seq[Pattern]) = {
    val stand_filter = new Standardize
    stand_filter.setInputFormat(patts.head.dataset())
    stand_filter
  }

  def applyFilterChangingOrder(patts: Seq[Pattern], filter: Standardize) = if (patts.isEmpty) Seq()
  else {
    val instances = patterns2instances(patts)
    val newInstances = Filter.useFilter(instances, filter) //Weka Filter clones every instance.
    val patterns = newInstances.zip(patts).map {
        case (newinst, patt) => Pattern(patt.id, newinst, false, patt.parent)
        case x => throw new Error("Problemas desconhecidos aplicando filter: " + x)
      }
    patterns.sortBy(_.vector.toString()) //to avoid undeterminism due to crazy weka filter behavior (it is probably multithreaded)
  }

  /**
   * Create a new Instances that contains Pattern objects instead of Instance objects.
   * @param patterns
   * @return
   */
  def patterns2instances(patterns: Seq[Pattern]) = if (patterns.isEmpty) {
    println("Empty sequence of patterns; cannot generate Weka Instances object.")
    throw new Error("Empty sequence of patterns; cannot generate Weka Instances object.")
    sys.exit(0)
  } else {
    val new_instances = new Instances(patterns.head.dataset, 0, 0)
    patterns foreach new_instances.add
    new_instances
  }

  def applyFilter(patts: Seq[Pattern], filter: Standardize) = if (patts.isEmpty) Seq()
  else {
    val ids = patts.map(_.id)
    val instances = patterns2instances(patts)
    val newInstances = Filter.useFilter(instances, filter) //Weka Filter clones every instance.
    val patterns = newInstances.zip(patts).map {
        case (newinst, patt) => Pattern(patt.id, newinst, false, patt.parent)
        case x => throw new Error("Problemas desconhecidos aplicando filter: " + x)
      }
    ids.map(id => patterns.find(_.id == id).getOrElse {
      println("Impossivel reordenar after z-score filter.")
      sys.exit(0)
    })
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
    //not todo: lazy requer que não retorne Either/Option
    val arq = new File(path + "/" + dataset + ".db")
    if (!arq.exists()) {
      println(s"Dataset file $arq not found!")
      Thread.sleep(1000)
      sys.exit(0)
    } else {
      lazy val patterns = {
        try {
          println(s"Opening $arq")
          val query = new InstanceQuery()
          query.setDatabaseURL("jdbc:sqlite:////" + arq)
          query.setQuery("select * from inst order by rowid")
          query.setDebug(false)
          val instances = query.retrieveInstances()
          instances.setClassIndex(instances.numAttributes() - 1)
          instances.setRelationName(dataset)
          val parent = PatternParent(instances)
          val res = instances.zipWithIndex.map { case (instance, idx) => Pattern(idx + 1, instance, false, parent)} //rowid is always > 0
          query.close()
          res.toStream
        }
        catch {
          case ex: IOException =>
            println("Problems reading file " + arq + ": " + ex.getMessage)
            Thread.sleep(1000)
            sys.exit(0)
        }
      }
      Lazy(patterns)
    }
  }
}

object TestFilter extends App {
  val d = Datasets.arff(true)("/home/davi/wcs/ucipp/uci/iris.arff", false).right.get.toList
  //.drop(10).take(5).toList
  val f = Datasets.zscoreFilter(d)
  val d2 = Datasets.applyFilterChangingOrder(d, f)
  //  d2.take(10) map (p => println(p.id + " " + p))
  //  d.take(10) map (p => println(p.id + " " + p))
  d2.sortBy(_.id).take(10) map (p => println(p.id + " " + p))
  d.sortBy(_.id).take(10) map (p => println(p.id + " " + p))
}