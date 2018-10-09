name := "Destroyer"

version := "1.0"

scalaVersion := "2.12.7"

val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5"
val gigahorse = "com.eed3si9n" %% "gigahorse-okhttp" % "0.3.1"
val playJson  = "com.typesafe.play" %% "play-json" % "2.6.9"

lazy val destroyer = (project in file("."))
  .settings(
    libraryDependencies += scalaTest % Test
  )
