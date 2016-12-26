name := "mls"

version := "0.3"

scalaVersion := "2.12.1"

libraryDependencies += "nz.ac.waikato.cms.weka" % "weka-dev" % "3.7.11"

libraryDependencies += "nz.ac.waikato.cms.weka" % "LibSVM" % "1.0.6"

libraryDependencies += "de.bwaldvogel" % "liblinear" % "1.95"

libraryDependencies += "com.googlecode.matrix-toolkits-java" % "mtj" % "1.0.1"

libraryDependencies += "org.scala-lang.modules" % "scala-parser-combinators_2.11" % "1.0.4"

libraryDependencies += "nz.ac.waikato.cms.weka" % "rotationForest" % "1.0.3"

//libraryDependencies += "com.googlecode.efficient-java-matrix-library" % "ejml" % "0.25"

scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature", "-language:reflectiveCalls")

scalacOptions ++= Seq("-Xmax-classfile-name","143")
