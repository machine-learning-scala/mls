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

import util.Datasets


object TestForgetting extends App {
  val d = Datasets.arff("/home/davi/wcs/ucipp/uci/abalone-11class.arff").right.get.toList.take(500)
  val f = Datasets.zscoreFilter(d)
  val df = Datasets.applyFilterChangingOrder(d, f)
  val ln = NB() //can forget

  val lh = VFDT() //can forget

  //  val lk = KNNinc(5, "eucl", df) //KNN cannot forget

  var n = ln.build(df.take(df.head.nclasses))
  var h = lh.build(df.take(df.head.nclasses))
  val fast_mutable = true

  val l0 = df.drop(df.head.nclasses).map { p =>
    n = ln.update(n, fast_mutable)(p)
    h = lh.update(h, fast_mutable)(p)
    (n.accuracy(df), h.accuracy(df))
  }
  val l1 = df.drop(df.head.nclasses).reverse.map { p =>
    val np = p.relabeled_reweighted(p.label, -p.weight(), new_missed = false)
    n = ln.update(n, fast_mutable)(np)
    h = lh.update(h, fast_mutable)(np)
    (n.accuracy(df), h.accuracy(df))
  }.reverse
  println(s"nb vfdt nb- vfdt-")
  l0.zip(l1).foreach { case ((nb0, vf0), (nb1, vf1)) =>
    println(s"$nb0 $vf0 $nb1 $vf1 ")
  }
  //  list.reverse.foreach { case (a, b, c) =>
  //    println(s"forgetting reversed $a $b $c")
  //  }
}

object TestSingleForgetting extends App {
  val d = Datasets.arff("/home/davi/wcs/ucipp/uci/abalone-11class.arff").right.get.toList.take(500)
  val f = Datasets.zscoreFilter(d)
  val df = Datasets.applyFilterChangingOrder(d, f)
  val dtr = Datasets.applyFilterChangingOrder(d, f).take(250)
  val dts = Datasets.applyFilterChangingOrder(d, f).drop(250)
  val fast_mutable = true

  //normal
  val ln = NB()
  val lh = VFDT()
  var n = ln.build(dtr.take(dtr.head.nclasses))
  var h = lh.build(dtr.take(dtr.head.nclasses))
  val l0 = dtr.drop(dtr.head.nclasses).map { p =>
    n = ln.update(n, fast_mutable)(p)
    h = lh.update(h, fast_mutable)(p)
    (n.accuracy(dts), h.accuracy(dts))
  }

  //põe-tira
  var n2 = ln.build(dtr.take(dtr.head.nclasses))
  var h2 = lh.build(dtr.take(dtr.head.nclasses))
  val l1 = dtr.drop(dtr.head.nclasses).map { p =>
    dts.foreach { p2 =>
      n2 = ln.update(n2, fast_mutable)(p2)
      h2 = lh.update(h2, fast_mutable)(p2)
      val np = p2.relabeled_reweighted(p2.label, -p2.weight(), new_missed = false)
      n2 = ln.update(n2, fast_mutable)(np)
      h2 = lh.update(h2, fast_mutable)(np)
    }
    n2 = ln.update(n2, fast_mutable)(p)
    h2 = lh.update(h2, fast_mutable)(p)
    (n2.accuracy(dts), h2.accuracy(dts))
  }

  l0.zip(l1).foreach { case ((a0, a1), (b0, b1)) =>
    println(s"$a0 $b0 $a1 $b1 ")
  }
}