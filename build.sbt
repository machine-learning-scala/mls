name := "mls"

version := "0.2"

scalaVersion := "2.10.4"

libraryDependencies += "nz.ac.waikato.cms.weka" % "weka-dev" % "3.7.11"

libraryDependencies += "nz.ac.waikato.cms.weka" % "LibSVM" % "1.0.6"

libraryDependencies += "com.googlecode.matrix-toolkits-java" % "mtj" % "1.0.1"

//libraryDependencies += "com.googlecode.efficient-java-matrix-library" % "ejml" % "0.25"

scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature", "-language:reflectiveCalls")
