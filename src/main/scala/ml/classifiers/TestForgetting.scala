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
  val d = Datasets.arff(bina = true)("/home/davi/wcs/ucipp/uci/abalone-11class.arff", zscored = false).right.get.toList.take(500)
  val f = Datasets.zscoreFilter(d)
  val df = Datasets.applyFilterChangingOrder(d, f)
  val ln = NB() //can forget

  val lh = VFDT() //can forget

  val lk = KNNinc(5, "eucl", df) //KNN cannot forget

  var n = ln.build(df.take(df.head.nclasses))
  var h = lh.build(df.take(df.head.nclasses))
  var k = lk.build(df.take(df.head.nclasses))
  val fast_mutable = true

  df.drop(df.head.nclasses).foreach { p =>
    n = ln.update(n, fast_mutable)(p)
    h = lh.update(h, fast_mutable)(p)
    k = lk.update(k, fast_mutable)(p)
    println(s"updating ${n.accuracy(df)} ${h.accuracy(df)} ${k.accuracy(df)}")
  }
  val list = df.drop(df.head.nclasses).reverse.map { p =>
    val np = p.relabeled_reweighted(p.label, -p.weight(), new_missed = false)
    n = ln.update(n, fast_mutable)(np)
    h = lh.update(h, fast_mutable)(np)
    k = lk.update(k, fast_mutable)(np)
    (n.accuracy(df), h.accuracy(df), k.accuracy(df))
  }
  list.reverse.foreach { case (a, b, c) =>
    println(s"forgetting reversed $a $b $c")
  }
}