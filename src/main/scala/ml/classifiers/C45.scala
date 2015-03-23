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

import ml.Pattern
import ml.models.{Model, WekaModel}
import util.Datasets
import weka.classifiers.Classifier
import weka.classifiers.trees.J48

import scala.util.parsing.combinator.{ImplicitConversions, JavaTokenParsers, RegexParsers}

case class C45(laplace: Boolean = true, minobjs: Int = -1) extends BatchWekaLearner {
   override val toString = s"C4.5w"
   val id = 666003
   val abr = toString
   val boundaryType = "rígida"
   val attPref = "ambos"

   //  private def complexity(classifier: Classifier) = classifier match {
   //    //      case sgd: SGD =>
   //    case rf: RF => rf.toString.lines.filter(_.contains("Size of the tree")).map(_.split(':').last.toDouble).sum
   //    case j48: J48 => j48.measureNumLeaves * j48.measureTreeSize
   //    case jrip: JRip => val output = jrip.toString.lines.dropWhile(!_.contains("JRIP rules")).drop(3).toList
   //      lazy val nrules = output.filter(_.contains("Number of Rules")).toList.headOption match {
   //        case Some(txt) => txt.split(':').last.toDouble
   //        case None => println("Number of rules not found!")
   //          sys.exit(1)
   //      }
   //      val ands = output.takeWhile(_.contains("=")).map(_.dropRight(1)).mkString.split(')').length.toDouble
   //      ands
   //  }
   def expected_change(model: Model)(pattern: Pattern): Double = ???

   def build(patterns: Seq[Pattern]): Model = {
      val classifier = new J48
      classifier.setMinNumObj(if (minobjs == -1) math.min(10, patterns.head.nclasses * 2) else minobjs)
      classifier.setUseLaplace(laplace)
      classifier.setDoNotCheckCapabilities(true)
      generate_model(classifier, patterns)
   }

   protected def test_subclass(classifier: Classifier) = classifier match {
      case cla: J48 => cla
      case _ => throw new Exception(this + " requires J48.")
   }

   def tree(model: Model) = model.asInstanceOf[WekaModel].classifier.toString.replace("extbf", "\\textbf").replace("%", "\\%").replace("#", "\\#") //.asInstanceOf[J48]
}


sealed class Tree

case class Root(children: List[Tree]) extends Tree

trait Obj extends Tree {
   val operador: String
   val valor: String
   val cond: String
}

case class Node(cond: String, operador: String, valor: String, children: List[Tree]) extends Obj

case class Leaf(cond: String, operador: String, valor: String, texto: String, pureza: Int) extends Obj

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

   def expr = "J48 pruned tree\n" ~> children ^^ Root

   def children = "[" ~> rep(obj) <~ "]"

   def obj: Parser[Tree] = se ~ children ^^ Node | seLeaf ~ (":" ~> texto <~ "(") ~ floatingPointNumber ~ ("/" ~> floatingPointNumber <~ ")") ^^ {
      case a ~ b ~ c ~ d ~ e ~ f => Leaf(a, b, c, d, (100 * (e.toDouble - f.toDouble) / e.toDouble).round.toInt)
   } | seLeaf ~ (":" ~> texto <~ "(") ~ (floatingPointNumber <~ ")") ^^ {
      case a ~ b ~ c ~ d ~ e => Leaf(a, b, c, d, 100)
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
   val l = C45(laplace = false, 30)
   val m = l.build(ps)
   println(l.tree(m))
   trav(Parsing.parse(l.tree(m)))

   def trav(t: Tree): Unit = t match {
      case Root(children) =>
         val text = children.head.asInstanceOf[Obj].cond
         println( """\node[line width=0.3ex, decision] {""" + text + "}")
         children foreach trav
      case Node(cond, operador, valor, children) =>
         val text = children.head.asInstanceOf[Obj].cond
         println("child {node [decision, label=" + op(operador, valor) + "] {" + text + "}")
         children foreach trav
         println(s"}")
      case l@Leaf(cond, operador, valor, texto, pureza) =>
         println("child {node [outcome, label=" + op(operador, valor) + "] {" + texto + "}}")
      case _ => println(s"pau")
         sys.exit(0)
   }

   def op(operador: String, valor: String) = operador match {
      case "=" => valor
      case "<=" => "$\\leq" + ((valor.toDouble * 100).round / 100d) + "$"
      case ">" => "$>" + ((valor.toDouble * 100).round / 100d) + "$"
   }
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