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
import ml.classifiers.{KNN, NB}
import util.{Tempo, Datasets}

object NaiveBayesExample extends App {
  println( """
 mls Copyright (C) 2014 Davi Pereira dos Santos
--------------------------------------------------------------
 This program comes with ABSOLUTELY NO WARRANTY.
 This is free software, and you are welcome to redistribute it
 under certain conditions.
 Refer to LICENSE file for details.
--------------------------------------------------------------""")

  val data = Datasets.arff(bina = true)("/home/davi/wcs/ucipp/uci/teaching-assistant-evaluation.arff") match {
    case Right(x) => x
    case Left(str) => println("Could not load iris dataset from the program path: " + str); sys.exit(0)
  }
  util.Datasets.kfoldCV(data, k = 5, parallel = true) { (trainingSet, testingSet, fold, _) =>
    val (model, t) = Tempo.timev(KNN(5, "eucl").build(trainingSet))
    val acc = model.accuracy(testingSet).formatted("%2.2f")

    println("Fold " + fold + ": " + acc + " in " + t + "ms.")
  }
}
