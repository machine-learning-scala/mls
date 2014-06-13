name := "mls-scala"

version := "0.1"

scalaVersion := "2.10.4"

libraryDependencies += "nz.ac.waikato.cms.weka" % "weka-dev" % "3.7.11"

scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature", "-language:reflectiveCalls")
