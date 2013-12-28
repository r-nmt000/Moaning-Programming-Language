import sbt._
import sbt.Keys._

object ProjectBuild extends Build {

  lazy val root = Project(
    id = "root",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      name := "MoaningProgrammingLanguage",
      organization := "com.github.r_nmt000",
      version := "0.1",
      scalaVersion := "2.10.3",
      libraryDependencies ++= Seq(
        // test
        "org.specs2" %% "specs2" % "2.3.6" % "test",
            
        //log
        "org.clapper" %% "grizzled-slf4j" % "latest.integration",
        "ch.qos.logback" % "logback-classic" % "latest.integration"

        //useful library
        // "org.apache.commons" % "commons-lang3" % "latest.integration",
        // "commons-io" % "commons-io" % "latest.integration"
      )
      // add other settings here
    )
  )
}
