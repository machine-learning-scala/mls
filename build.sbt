name := "mls"

version := "0.1"

scalaVersion := "2.10.4"

libraryDependencies += "nz.ac.waikato.cms.weka" % "weka-dev" % "3.7.11"

libraryDependencies += "com.googlecode.matrix-toolkits-java" % "mtj" % "1.0.1"

scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature", "-language:reflectiveCalls")
