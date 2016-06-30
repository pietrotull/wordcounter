organization  := "fi.piet"

name := "wordcounter"

version := "1.0"

scalaVersion := "2.11.8"

mainClass := fi.piet.WordCounter

val scalatest = "org.scalatest" %% "scalatest" % "2.2.6" % "test"

libraryDependencies ++= Seq(scalatest)

Revolver.settings