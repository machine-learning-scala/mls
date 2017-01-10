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
package ml.models

import java.io.{File, FileWriter}
import java.nio.file.Files

import ml.Pattern
import traits.Util

import scala.sys.process._

trait Model extends Util {
  def heatmap(arq: String, ts: Vector[Pattern], f: (Array[Double]) => Double, symbols: Seq[(Seq[Pattern], String)] = Seq()) {
    def plot(exs: Seq[Pattern], symb: String) = {
      val coords = exs.map(p => p.a -> p.b).mkString(" ")
      s"\\addplot[only marks, $symb]\ncoordinates{$coords};\n"
    }

    val prefix = s"/run/shm/$arq"
    val tex = s"$prefix.tex"
    val csv = s"$prefix.csv"
    try {
      new File(tex).delete()
      new File(s"$arq.pdf").delete()
    } catch {
      case ex: Throwable =>
    }
    Files.copy(new File("heatmap.tex").toPath, new File(tex).toPath)

    val tsSorted = ts.sortBy(_.a).sortBy(_.b)
    val rows = tsSorted.map(_.b).distinct.size
    replaceInFile(tex, "mesh/rows=", ", shader=", rows.toString)
    replaceInFile(tex, "file", "};", "[skip first] {" + csv)
    if (symbols.nonEmpty) {
      val plots = symbols map { case (lst, symb) => plot(lst, symb) }
      replaceInFile(tex, "%other plots", "\n\\begin{axis}[axis lines=none]" + plots.mkString("\n") + "\\end{axis}\n")
    }

    val fw = new FileWriter(csv)
    fw.write("x y z\n")
    var (vmn, vmx) = Double.MinValue -> Double.MaxValue
    tsSorted foreach { p =>
      val d = distribution(p)
      val (x, y, v) = (p.a.toInt, p.b.toInt, f(d))
      if (v > vmx) vmx = v
      if (v < vmn) vmn = v
      fw.write(s"$x $y $v\n")
    }
    fw.close()

    println(Seq("pdflatex", tex).!!.split("\n").toList.mkString("\n"))
    //    new File(s"$arq.pdf").renameTo(new File(s"$arq.pdf"))
    Seq("okular", s"$arq.pdf").run

    // R
    //          fw = new FileWriter("/run/shm/knowledge-boundary.r")
    //          fw.write(script(tsSorted.map(_.a).max.toInt, tsSorted.map(_.b).max.toInt, (vmx + vmn) / 2))
    //          fw.close()
    //    var log = (Seq("Rscript", "--vanilla", "/run/shm/knowledge-boundary.r") !!).split("\n").toList
    //    println(log.mkString("\n"))
    //    println("===========================================")
    //    log = (Seq("okular", "Rplots.pdf") !!).split("\n").toList
  }

  def JS(pattern: Pattern): Double

  def predict(instance: Pattern): Double

  /**
    * Mostra qtas vezes a classe da linha foi predita como a classe da coluna.
    *
    * @param patts
    */
  def confusion(patts: Seq[Pattern]) = if (patts.isEmpty) {
    println("Empty list of patterns at confusion matrix.")
    sys.exit(1)
  } else {
    val nc = patts.head.nclasses
    val n = patts.size
    val res = Array.fill(nc)(Array.fill(nc)(0))
    var i = 0
    while (i < n) {
      val p = patts(i)
      res(p.label.toInt)(predict(p).toInt) += 1
      i += 1
    }
    res
  }

  def distribution(instance: Pattern): Array[Double]

  def predictionEntropy(patts: Seq[Pattern]) = if (patts.isEmpty) {
    println("Empty list of patterns at predictionEntropy.")
    sys.exit(1)
  } else {
    val ents = patts.map(x => normalized_entropy(distribution(x)))
    media_desvioPadrao(ents.toVector)
  }

  protected def normalized_entropy(P: Array[Double]) = -P.map(x => x * log(x)).sum / log(P.length)

  protected def log(x: Double) = if (x == 0) 0d else math.log(x)

  protected def media_desvioPadrao(items: Vector[Double]) = {
    val s = items.sum
    val l = items.length.toDouble
    val m = s / l
    val v0 = (items map {
      x =>
        val di = x - m
        di * di
    }).sum / (l - 1)
    val v = if (v0.isNaN) 0 else v0
    val d = math.sqrt(v)
    (m, d)
  }

  def output(instance: Pattern): Array[Double]

  //  {
  //    val dist = distribution(instance)
  //    val nclasses = instance.nclasses
  //    var c = 0
  //    var max = 0d
  //    var cmax = 0
  //    while (c < nclasses) {
  //      val v = dist(c)
  //      if (v > max) {
  //        max = v
  //        cmax = c
  //      }
  //      c += 1
  //    }
  //    cmax
  //  }

  def accuracy(patterns: Seq[Pattern], n: Double = -1) = {
    hits(patterns) / (if (n == -1) patterns.length.toDouble else n)
  }

  def hits(patterns: Seq[Pattern]) = patterns.count(hit) //weka is not thread-safe to parallelize hits()

  def hits_and_qtd_per_class(patterns: Seq[Pattern]) = {
    ??? //inefficient
    (0 until patterns.head.nclasses) map {
      c =>
        val hits_for_this_class = patterns.filter(_.label == c)
        val hits = (hits_for_this_class map hit) count (_ == true)
        (hits, hits_for_this_class.length)
    }
  }

  def hit(instance: Pattern) = instance.label == predict(instance)
}

//trait IncrementalModel extends Model

trait BatchModel extends Model {
  val training_set: Vector[Pattern]
}


//  val size: Double

//  def distributions(instance: Pattern): Seq[Array[Double]]

//  def distribution(instance: Pattern) = {
//    val dists = distributions(instance)
//    val dist = dists(0)
//    val nclasses = instance.nclasses
//    var c = 0
//    while (c < nclasses) {
//      var d = 1
//      while (d < size) {
//        dist(c) += dists(d)(c)
//        d += 1
//      }
//      dist(c) /= size
//      c += 1
//    }
//    dist
//    //    distributions(instance).transpose.map(_.sum / size).toArray
//  } //average between distributions (is it the same as adding and normalizing?)

//  /**
//   * Hard prediction for a given instance.
//   * In the case of ensembles, hard vote will be performed.
//   * @param instance
//   * @return
//   */
//  def predict(instance: Pattern) = {
//    val dists = distributions(instance) //weka classifyInstance() also internally falls back to distributionForInstance()
//    val nclasses = instance.nclasses
//    val votes = new Array[Int](nclasses)
//    var d = 0
//    while (d < size) {
//      var c = 0
//      var max = 0d
//      var cmax = 0
//      while (c < nclasses) {
//        val v = dists(d)(c)
//        if (v > max) {
//          max = v
//          cmax = c
//        }
//        c += 1
//      }
//      votes(cmax) += 1
//      d += 1
//    }
//    var c = 0
//    var max = 0
//    var cmax = 0
//    while (c < nclasses) {
//      val v = votes(c)
//      if (v > max) {
//        max = v
//        cmax = c
//      }
//      c += 1
//    }
//    cmax
//  }
