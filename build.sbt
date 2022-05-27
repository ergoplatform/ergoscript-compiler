name := "ErgoScriptCompiler"

updateOptions := updateOptions.value.withLatestSnapshots(false)

ThisBuild / version := "1.0"

scalaVersion := "2.12.10"

resolvers ++= Seq(
  "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/",
  "SonaType" at "https://oss.sonatype.org/content/groups/public",
  "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
)

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-json" % "2.9.1",
  "io.github.ergoplatform" %% "kiosk" % "1.0",
  "com.squareup.okhttp3" % "mockwebserver" % "3.14.9" % Test,
  "org.scalatest" %% "scalatest" % "3.0.8" % Test,
  "org.scalacheck" %% "scalacheck" % "1.14.+" % Test,
  "org.mockito" % "mockito-core" % "2.8.47" % Test
)

updateOptions := updateOptions.value.withLatestSnapshots(false)

mainClass in (Compile, run) := Some("Compile")

assemblyMergeStrategy in assembly := {
  case PathList("reference.conf")    => MergeStrategy.concat
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x                             => MergeStrategy.first
}
