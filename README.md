mls
===
Wrappers for Weka for Scala (at least at the moment).

Installation
------------

1- Install sbt:
http://www.scala-sbt.org/release/tutorial/Installing-sbt-on-Linux.html

2- Clone repo:
git clone https://github.com/machine-learning-scala/mls.git

3- Run included example:
cd mls
sbt run


Use as a library
----------------

1- Add a file 'Build.scala' to the 'project' folder of your own project with the contents:
import sbt._

object MyBuild extends Build {

  lazy val root = Project("root", file(".")) dependsOn(mlsProj)
  lazy val mlsProj = RootProject(uri("git://github.com/machine-learning-scala/mls.git"))

}

2- Be happy.
