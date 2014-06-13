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
package ml.clusterers

import ml.Pattern
import ml.clusterers.HC.HClusterer
import util.Datasets
import weka.clusterers.{Node, HierarchicalClusterer2}

object HC {
  type LeafID = Int
  type NodeID = Int

  case class HierarchicalClustererScala(pool: Array[Pattern]) extends HierarchicalClusterer2 {
    val empty_freqs = List.fill(pool.head.nclasses)(0)
    lazy val tree = {
      //    wekahc.setDistanceFunction()
      setOptions(weka.core.Utils.splitOptions("-L WARD"))
      setNumClusters(1)
      setPrintNewick(true)
      buildClusterer(Datasets.patterns2instances(pool))
      //      println(graph())
      scan(m_clusters(0))
    }

    var leaf_counter = -1
    var node_counter = 2 * pool.length - 1

    def l = {
      leaf_counter += 1
      leaf_counter
    }

    def n = {
      node_counter -= 1
      node_counter
    }

    val parent = Array.fill(pool.length * 2 - 1)(-1)

    def scan(node: Node): TreeNode = {
      val new_node = node match {
        case x if x.m_left == null && x.m_right == null => Branch(n, Leaf(x.m_iLeftInstance, pool(x.m_iLeftInstance)), Leaf(x.m_iRightInstance, pool(x.m_iRightInstance)))
        case x if x.m_left == null && x.m_right != null => Branch(n, Leaf(x.m_iLeftInstance, pool(x.m_iLeftInstance)), scan(x.m_right))
        case x if x.m_left != null && x.m_right == null => Branch(n, scan(x.m_left), Leaf(x.m_iRightInstance, pool(x.m_iRightInstance)))
        case x if x.m_left != null && x.m_right != null => Branch(n, scan(x.m_left), scan(x.m_right))
      }
      parent(new_node.left.id) = new_node.id
      parent(new_node.right.id) = new_node.id
      new_node
    }
  }

  /**
   * Hierarchical clusterer.
   * Uses Weka Ward method.
   */
  case class HClusterer(pool: Seq[Pattern]) {
    val wekahc = new HierarchicalClustererScala(pool.toArray)
    val tree = wekahc.tree
//    wekahc.tree.display()
    lazy val parent_vector = wekahc.parent
  }

  /**
   * http://stackoverflow.com/questions/9129671/change-node-in-scala-case-class-tree
   */
  sealed abstract class TreeNode(val id: LeafID) {
    /** Replaces this node or its children according to the given function */
    def replace(fn: TreeNode => TreeNode): TreeNode

    //  def replace(freqs: Seq[Int], node: Node): Node =
    //    replace(n => if (n.freqs == freqs) node else n)

    def display(level: Int = 0)
  }

  case class Leaf(override val id: LeafID, pattern: Pattern) extends TreeNode(id) {
    def replace(fn: TreeNode => TreeNode): TreeNode = fn(this)

    def display(level: Int = 0) {
      println(("  " * level) + id + ": " + pattern)
    }
  }

  case class Branch(override val id: NodeID, left: TreeNode, right: TreeNode) extends TreeNode(id) {
    def replace(fn: TreeNode => TreeNode): TreeNode = {
      val newSelf = fn(this)

      if (this eq newSelf) {
        // this node's value didn't change, check the children
        val newLeft = left.replace(fn)
        val newRight = right.replace(fn)

        if ((left eq newLeft) && (right eq newRight)) {
          // neither this node nor children changed
          this
        } else {
          // change the children of this node
          copy(left = newLeft, right = newRight)
        }
      } else {
        // change this node
        newSelf
      }
    }

    def display(level: Int = 0) {
      printf("%s%s\n", "  " * level, id)
      left.display(level + 1)
      right.display(level + 1)
    }
  }

}

object Test extends App {
  val data = Datasets.arff(bina = false)("/home/davi/unversioned/experimentos/fourclusters.arff").right.get._1.distinct
  val train = data.take(1500)
  val hc = HClusterer(train)
  //    val patts = Data.arff("/home/davi/working-copies/arff/twoclusters.arff").right.get.distinct //.take(22)
  hc.tree.display()
  println(hc.parent_vector.toList)
  println(hc.wekahc)
}

