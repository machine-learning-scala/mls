package weka.classifiers.trees


trait JSMeasure extends EntropyMeasure {
   def normalizedJSdivergence(distributions: Seq[Array[Double]]) = JSdivergence(distributions) / log2(distributions.size)

   def JSdivergence(distributions: Seq[Array[Double]]) = {
      val tot = distributions.map(_.sum).sum
      val mean_distribution = distributions.reduce((a, b) => a.zip(b).map(p => p._1 + p._2)) map (_ / tot)
      val ents = distributions.map(entropy).toList
      val mean_entropy = ents.sum / ents.size
      entropy(mean_distribution) - mean_entropy
   }
}
