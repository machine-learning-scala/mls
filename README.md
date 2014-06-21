mls
===
Wrappers for Weka for Scala (at least at the moment).

Installation
------------

* Install [sbt](http://www.scala-sbt.org/release/tutorial/Installing-sbt-on-Linux.html "installing sbt")

* Clone repo:
```
    git clone https://github.com/machine-learning-scala/mls.git
```

* Run included example:
```
    cd mls
    sbt run
```


Use as a library
----------------

* Add a file 'Build.scala' to the 'project' folder of your own project with the contents:
```
    import sbt._

    object MyBuild extends Build {

      lazy val root = Project("root", file(".")) dependsOn(mlsProj)
      lazy val mlsProj = RootProject(uri("https://github.com/machine-learning-scala/mls.git"))

    }
```

* Be happy.
