ThisBuild / organization := "io.github.ergoplatform"
ThisBuild / organizationName := "ergoplatform"
ThisBuild / organizationHomepage := Some(url("https://www.ergoplatform.org"))

ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/ergoplatform/ergoscript-compiler"),
    "scm:git@github.ergoplatform/ergoscript-compiler.git"
  )
)

ThisBuild / developers := List(
  Developer(
    id = "scalahub",
    name = "scalahub",
    email = "23208922+scalahub@users.noreply.github.com",
    url = url("https://www.ergoplatform.org")
  )
)

ThisBuild / description := "Standalone tool for compiling ErgoScript to ErgoTree and Address"
ThisBuild / licenses := List("The Unlicense" -> new URL("https://unlicense.org/"))
ThisBuild / homepage := Some(url("https://github.com/ergoplatform/ergoscript-compiler"))

// Remove all additional repository other than Maven Central from POM
ThisBuild / pomIncludeRepository := { _ =>
  false
}

ThisBuild / publishTo := {
  val nexus = "https://s01.oss.sonatype.org/"
  if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
  else Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

ThisBuild / publishMavenStyle := true

ThisBuild / versionScheme := Some("early-semver")
