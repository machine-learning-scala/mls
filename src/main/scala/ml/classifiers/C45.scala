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

import java.io.PrintWriter
import java.text.DecimalFormat

import ml.Pattern
import ml.models.{Model, WekaModel}
import util.Datasets
import weka.classifiers.Classifier
import weka.classifiers.trees.J48

import scala.collection.mutable
import scala.util.parsing.combinator.{ImplicitConversions, JavaTokenParsers, RegexParsers}

case class C45(laplace: Boolean = true, minobjs: Int = -1, explicitos: Double = 1) extends BatchWekaLearner {
   override val toString = s"C4.5w"
   val id = 666003
   val abr = toString
   val boundaryType = "rígida"
   val attPref = "ambos"
   val min = if (minobjs == -1) 1 else minobjs

   def expected_change(model: Model)(pattern: Pattern): Double = ???

   def build(patterns: Seq[Pattern]): Model = {
      val classifier = new J48
      classifier.setMinNumObj(if (minobjs == -1) math.min(10, patterns.head.nclasses * 2) else minobjs)
      classifier.setUseLaplace(laplace)
      classifier.setDoNotCheckCapabilities(true)
      //      classifier.setConfidenceFactor(confidence.toFloat)
      //      classifier.setUnpruned(!prune)
      //      classifier.setSaveInstanceData(true)
      generate_model(classifier, patterns)
   }

   protected def test_subclass(classifier: Classifier) = classifier match {
      case cla: J48 => cla
      case _ => throw new Exception(this + " requires J48.")
   }

   //   class MyMap[K, V](m1: Map[K, V]) {
   //      def merge(m2: Map[K, V])(f: (V, V) => V) = (m1 -- m2.keySet) ++ (m2 -- m1.keySet) ++ (for (k <- m1.keySet & m2.keySet) yield {
   //         k -> f(m1(k), m2(k))
   //      })
   //   }
   //
   //   implicit def toMyMap[K, V](m: Map[K, V]) = new MyMap(m)
   //
   //   def add(mm: mutable.Map[String, Int], m: Map[String, Int]) = m foreach { case (k, v) =>
   //      val s = mm.getOrElseUpdate(k, 0)
   //      mm(k) = s + v
   //   }

   //   def travDistr(t: Tree): Map[String, Int] = t match {
   //      case Node(cond, operador, valor, children) => (children map travDistr) reduceLeft {
   //         _.merge(_)(_ + _)
   //      }
   //      case l@Leaf(cond, operador, valor, texto, qtds) => qtds
   //      case _ => sys.error(s"erro matching")
   //   }

   def trav(t: Tree): String = t match {
      case Root(children) =>
         """\node[line width=0.3ex, decision] {""" + children.head.asInstanceOf[Obj].cond + "}\n" + (children map trav).mkString("\n")
      case n@Node(cond, operador, valor, children) =>
         "child {node [decision] {" + children.head.asInstanceOf[Obj].cond + "}\n" + (children map trav).mkString("\n") + "edge from parent node [cond] {" + op(operador, valor) + "}}"
      case l@Leaf(cond, operador, valor, texto, qtds) =>
         val tot = qtds.values.sum
         if (tot > 0) {
            var s = 0
            val (bef, aft) = qtds.toList.sortBy(_._2).reverse span { case (k, v) =>
               s += v
               s < explicitos * tot
            }
            val qtds2 = bef :+ aft.head
            val demais = tot - qtds2.map(_._2).sum
            val qtds3 = if (demais > 0) qtds2 ++ aft.tail.map(x => "% " + x._1 -> x._2) :+ ("demais" -> demais) else qtds2
            "child {node [outcome] {\n" + qtds3.map(x => x._1 + ": " + x._2).mkString("\\\\\n") + "} edge from parent node [cond] {" + op(operador, valor) + "}}"
         } else "%"
      case _ => sys.error(s"erro matching")
   }

   def op(operador: String, valor: String) = operador match {
      case "=" => valor
      case "<=" => "$\\leq" + new DecimalFormat("##.#").format(valor.toDouble) + "$"
      case ">" => "$>" + new DecimalFormat("##.#").format(valor.toDouble) + "$"
      case _ => sys.error("pau")
   }

   def tree(arff: String, tex: String) = {
      Datasets.arff(arff, dedup = false) match {
         case Left(str) => throw new Error(str)
         case Right(ps) =>
            val m = build(ps)
            val str = m.asInstanceOf[WekaModel].classifier.asInstanceOf[J48].distrs().replace("extbf", "\\textbf")
            println(s"")
            println(str)
            println(s"")
            val fw2 = new PrintWriter(tex, "ISO-8859-1")
            val r = trav(Parsing.parse(str)) + "\n;"
            fw2.write(r)
            fw2.close()
         //            println(s"")
         //            println(r)
         //            println(s"")
      }
   }

   def str(m: Model) = cast2wekabatmodel(m).classifier.toString
}


sealed class Tree

case class Root(children: List[Tree]) extends Tree

trait Obj extends Tree {
   val operador: String
   val valor: String
   val cond: String
}

case class Node(cond: String, operador: String, valor: String, children: List[Tree]) extends Obj

case class Leaf(cond: String, operador: String, valor: String, texto: String, qtds: Map[String, Int]) extends Obj

object Parsing extends RegexParsers with ImplicitConversions with JavaTokenParsers {

   def pre(str: String) = {
      val s = "[" + str.replace("\r\n", "\n").replace("\r", "\n").split("Number of Leaves").head.replace("------------------\n\n", "[\n") //+ "\n]"
      val s2 = (s + "\n ").replace("|   ", "§").split('\n').map { x => val l = x.dropWhile(_ == '§'); (x.size - l.size) -> l}
      (s + "\n").replace("|   ", "§").split('\n').map { x => val l = x.dropWhile(_ == '§'); (x.size - l.size) -> l}.zip(s2.drop(1)).map { case (fst, snd) => if (snd._1 > fst._1) fst._2 + "[" else if (snd._1 < fst._1) fst._2 + Seq.fill(fst._1 - snd._1)("\n]").mkString else fst._2}.mkString("\n") + "\n]\n"
   }

   /**
    * Converts a text of code into an AST.
    * @param text source code
    * @return AST
    */
   def parse(text: String): Tree = {
      val u = parseAll(expr, pre(text))
      u match {
         case Success(t, next) => {
            val ug = u.get
            ug
         }
         case f => {
            throw new Exception("" + f)
         }
      }
   }

   def expr = children ^^ Root

   def children = "[" ~> rep(obj) <~ "]"

   def obj: Parser[Tree] = se ~ children ^^ Node |
      seLeaf ~ (":" ~> texto <~ "(") ~ (rep1sep(classe, ";") <~ ")") ~ ("ª" ~> rep1sep(classe, ";") <~ "º") ^^ {
         case a ~ b ~ c ~ d ~ seq1 ~ seq2 => Leaf(a, b, c, d, seq1.toList.zip(seq2.toList.map(_.toDouble.toInt)).toMap)
      }

   //weka toString original:
   //   def obj: Parser[Tree] = se ~ children ^^ Node |
   //      seLeaf ~ (":" ~> texto <~ "(") ~ floatingPointNumber ~ ("/" ~> floatingPointNumber <~ ")") ^^ {
   //         case a ~ b ~ c ~ d ~ e ~ f => Leaf(a, b, c, d, e.toDouble.toInt - f.toDouble.toInt, f.toDouble.toInt)
   //      } |
   //      seLeaf ~ (":" ~> texto <~ "(") ~ (floatingPointNumber <~ ")") ^^ {
   //         case a ~ b ~ c ~ d ~ e => Leaf(a, b, c, d, e.toDouble.toInt, 0) //, 100, e.toDouble.toInt)
   //      }

   def se = cond ~ """(=|<=|>)""".r ~ valor

   def seLeaf = cond ~ """(=|<=|>)""".r ~ valorLeaf

   def texto = classe <~ """(?=\()""".r

   def classe = """([^\[\]\n:\(\)=><;ªº])+""".r

   def valor = """([^\[\]\n:\(\)=><])+(?=\[)""".r

   def valorLeaf = """([^:])+(?=:)""".r

   def cond = """.+(?=( <= | = | > ))""".r
}


object C45Test extends App {
   val ps = Datasets.arff("/home/davi/wcs/ucipp/uci/metaTree.arff").right.get
   val l = C45(laplace = false)
   val m = l.build(ps)
   println(l.str(m))
}