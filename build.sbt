name := "ErgoScriptCompiler"

version := "0.1"

updateOptions := updateOptions.value.withLatestSnapshots(false)

scalaVersion := "2.12.10"

lazy val Kiosk = ProjectRef(uri("git://github.com/ergoplatform/ergo-jde.git"), "kiosk")

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-json" % "2.9.1",
  "com.squareup.okhttp3" % "mockwebserver" % "3.14.9" % Test,
  "org.scalatest" %% "scalatest" % "3.0.8" % Test,
  "org.scalacheck" %% "scalacheck" % "1.14.+" % Test,
  "org.mockito" % "mockito-core" % "2.8.47" % Test
)

lazy val root = (project in file("."))
  .dependsOn(
    Kiosk
  )
  .settings(
    updateOptions := updateOptions.value.withLatestSnapshots(false),
    mainClass in (Compile, run) := Some("Compile"),
    assemblyMergeStrategy in assembly := {
      case PathList("reference.conf")    => MergeStrategy.concat
      case PathList("META-INF", xs @ _*) => MergeStrategy.discard
      case x                             => MergeStrategy.first
    }
  )
