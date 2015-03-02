package weka.classifiers.trees

trait EntropyMeasure {
   protected def desb(P: Array[Double]) = (P.max - P.min) / (P.max + P.min)

   protected def log2(x: Double) = if (x == 0) 0d else math.log(x) / math.log(2d)

   protected def log(x: Double) = if (x == 0) 0d else math.log(x)

   protected def entropy(P: Array[Double]) = {
      //     println(P.toList + " P")
      //     println(P.map(x => x * log2(x)).toList )
      -P.map(x => x * log2(x)).sum
   }
}
