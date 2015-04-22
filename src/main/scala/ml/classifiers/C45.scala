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

import ml.Pattern
import ml.models.{Model, WekaModel}
import util.Datasets
import weka.classifiers.Classifier
import weka.classifiers.trees.J48

import scala.collection.mutable
import scala.util.parsing.combinator.{ImplicitConversions, JavaTokenParsers, RegexParsers}

case class C45(laplace: Boolean = true, minobjs: Int = -1, confidence: Double = 0.25, prune: Boolean = true) extends BatchWekaLearner {
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
      classifier.setConfidenceFactor(confidence.toFloat)
      classifier.setUnpruned(!prune)
      //      classifier.setSaveInstanceData(true)
      generate_model(classifier, patterns)
   }

   protected def test_subclass(classifier: Classifier) = classifier match {
      case cla: J48 => cla
      case _ => throw new Exception(this + " requires J48.")
   }

   class MyMap[K, V](m1: Map[K, V]) {
      def merge(m2: Map[K, V])(f: (V, V) => V) = (m1 -- m2.keySet) ++ (m2 -- m1.keySet) ++ (for (k <- m1.keySet & m2.keySet) yield {
         k -> f(m1(k), m2(k))
      })
   }

   implicit def toMyMap[K, V](m: Map[K, V]) = new MyMap(m)

   def add(mm: mutable.Map[String, Int], m: Map[String, Int]) = m foreach { case (k, v) =>
      val s = mm.getOrElseUpdate(k, 0)
      mm(k) = s + v
   }

   //mesmo sem podar, ficam ~10% das folhas impuras; isso estraga a contabilização, pois pode por como "demais" uma strat já explícita na folha
   //   é melhor perder esses exemplos sem nome do que parecer inconsistente
   def travDistr(t: Tree): Map[String, Int] = t match {
      //case Root(children) =>
      case Node(cond, operador, valor, children) => (children map travDistr) reduceLeft {
         _.merge(_)(_ + _)
      }
      case l@Leaf(cond, operador, valor, texto, qtd, demais) => Map(texto -> qtd)
      case _ => sys.error(s"erro matching")
   }

   def trav(t: Tree): String = t match {
      case Root(children) =>
         """\node[line width=0.3ex, decision] {""" + children.head.asInstanceOf[Obj].cond + "}\n" + (children map trav).mkString("\n")
      case n@Node(cond, operador, valor, children) =>
         val maps = children map travDistr
         val filhosSaudaveis = maps.map(_.values.sum).min >= min
         if (filhosSaudaveis) "child {node [decision] {" + children.head.asInstanceOf[Obj].cond + "}\n" + (children map trav).mkString("\n") + "edge from parent node [cond] {" + op(operador, valor) + "}}"
         else {
            val listaCrua = travDistr(n).toList.sortBy(_._2).reverse
            var s = 0
            val tot = maps.map(_.values.sum).sum
            val (listaMelhores0, _) = listaCrua.zipWithIndex.takeWhile { case ((k, v), i) =>
               s += v
               s <= confidence * tot || (i < listaCrua.size - 1 && listaCrua(i + 1)._2 == v) || i == listaCrua.size - 1
            }.unzip
            val listaMelhores = listaCrua.take(listaMelhores0.size + 1)
            val resto = listaCrua.drop(listaMelhores.size).map(_._2).sum
            val lista = listaMelhores ++ (if (resto > 0) Seq("demais" -> resto) else Seq())
            "child {node [outcome] {" + lista.map { case (k, v) => k.trim + ": " + v}.mkString("\\\\\n") + "} edge from parent node [cond] {" + op(operador, valor) + "}}"
         }
      case l@Leaf(cond, operador, valor, texto, qtd, demais) =>
         "child {node [outcome] {" + texto.trim + ": " + qtd + (if (demais > 0) "\\\\\ndemais: " + demais else "") + "} edge from parent node [cond] {" + op(operador, valor) + "}}"
      case _ => sys.error(s"erro matching")
   }

   def op(operador: String, valor: String) = operador match {
      case "=" => valor
      case "<=" => "$\\leq" + "%2.0f".format(valor.toDouble) + "$"
      case ">" => "$>" + "%2.0f".format(valor.toDouble) + "$"
      case _ => sys.error("pau")
   }

   def tree(arff: String, tex: String) = {
      Datasets.arff(arff, dedup = false) match {
         case Left(str) => throw new Error(str)
         case Right(ps) =>
            val l = C45(laplace = false, 1, 1, prune = false)
            val m = l.build(ps)
            val str = m.asInstanceOf[WekaModel].classifier.toString.replace("extbf", "\\textbf")
            println(s"")
            println(str)
            println(s"")
            val fw2 = new PrintWriter(tex, "ISO-8859-1")
            val r = trav(Parsing.parse(str)) + ";"
            fw2.write(r)
            fw2.close()
            println(s"")
            println(r)
            println(s"")
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

case class Leaf(cond: String, operador: String, valor: String, texto: String, qtd: Int, demais: Int) extends Obj

object Parsing extends RegexParsers with ImplicitConversions with JavaTokenParsers {

   def pre(str: String) = {
      val s = str.replace("\r\n", "\n").replace("\r", "\n").split("Number of Leaves").head.replace("------------------\n\n", "[\n")
      val s2 = (s + "\n ").replace("|   ", "§").split('\n').map { x => val l = x.dropWhile(_ == '§'); (x.size - l.size) -> l}
      (s + "\n").replace("|   ", "§").split('\n').map { x => val l = x.dropWhile(_ == '§'); (x.size - l.size) -> l}.zip(s2.drop(1)).map { case (fst, snd) => if (snd._1 > fst._1) fst._2 + "[" else if (snd._1 < fst._1) fst._2 + Seq.fill(fst._1 - snd._1)("\n]").mkString else fst._2}.mkString("\n") + "\n]\n"
   }

   /**
    * Converts a text of code into an AST.
    * @param text source code
    * @return AST
    */
   def parse(text: String): Tree = {
      //      println(text)
      //      println(s"pppppppppppPP")
      //      println(pre(text))
      val u = parseAll(expr, pre(text))
      u match {
         case Success(t, next) => {
            val ug = u.get
            //            ug.l map println
            ug
         }
         case f => {
            throw new Exception("" + f)
         }
      }
   }

   def expr = ("J48 unpruned tree\n" | "J48 pruned tree\n") ~> children ^^ Root

   def children = "[" ~> rep(obj) <~ "]"

   def obj: Parser[Tree] = se ~ children ^^ Node |
      seLeaf ~ (":" ~> texto <~ "(") ~ floatingPointNumber ~ ("/" ~> floatingPointNumber <~ ")") ^^ {
         case a ~ b ~ c ~ d ~ e ~ f => Leaf(a, b, c, d, e.toDouble.toInt - f.toDouble.toInt, f.toDouble.toInt)
      } |
      seLeaf ~ (":" ~> texto <~ "(") ~ (floatingPointNumber <~ ")") ^^ {
         case a ~ b ~ c ~ d ~ e => Leaf(a, b, c, d, e.toDouble.toInt, 0) //, 100, e.toDouble.toInt)
      }

   def se = cond ~ """(=|<=|>)""".r ~ valor

   def seLeaf = cond ~ """(=|<=|>)""".r ~ valorLeaf

   def texto = """([^\[\]\n:\(\)=><])+(?=\()""".r

   def valor = """([^\[\]\n:\(\)=><])+(?=\[)""".r

   def valorLeaf = """([^:])+(?=:)""".r

   def cond = """.+(?=( <= | = | > ))""".r
}


object C45Test extends App {
   val ps = Datasets.arff("/home/davi/wcs/ucipp/uci/metaTree.arff").right.get
   val l = C45(laplace = false, 30, 0.25, true)
   val m = l.build(ps)
   println(l.str(m))
}

/*
graph --------------
digraph J48Tree {
N0 [label="V2" ]
N0->N1 [label="<= 0.561"]
N1 [label="V2" ]
N1->N2 [label="<= -0.857"]
N2 [label="1 (1426.0/427.0)" shape=box style=filled ]
N1->N3 [label="> -0.857"]
N3 [label="V1" ]
N3->N4 [label="<= 0.155"]
N4 [label="2 (1149.0/180.0)" shape=box style=filled ]
N3->N5 [label="> 0.155"]
N5 [label="1 (1000.0/490.0)" shape=box style=filled ]
N0->N6 [label="> 0.561"]
N6 [label="1 (1716.0/487.0)" shape=box style=filled ]
}


prefix ---------------
[V2: <= 0.561,
 > 0.561[V2: <= -0.857,
 > -0.857[1 (1426.0/427.0)][V1: <= 0.155,
 > 0.155[2 (1149.0/180.0)][1 (1000.0/490.0)]]][1 (1716.0/487.0)]]

toSource ---------------
class bla {

  public static double classify(Object[] i)
    throws Exception {

    double p = Double.NaN;
    p = bla.N37374a5e0(i);
    return p;
  }
  static double N37374a5e0(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 1;
    } else if (((Double) i[1]).doubleValue() <= 0.561) {
    p = bla.N4671e53b1(i);
    } else if (((Double) i[1]).doubleValue() > 0.561) {
      p = 0;
    }
    return p;
  }
  static double N4671e53b1(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= -0.857) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() > -0.857) {
    p = bla.N2db7a79b2(i);
    }
    return p;
  }
  static double N2db7a79b2(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 1;
    } else if (((Double) i[0]).doubleValue() <= 0.155) {
      p = 1;
    } else if (((Double) i[0]).doubleValue() > 0.155) {
      p = 0;
    }
    return p;
  }
}
 */