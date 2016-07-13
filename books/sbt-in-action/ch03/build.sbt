name := "preowned-kittens"

val gitHeadCommitSha = taskKey[String]("Determines the current git commit SHA")
val makeVersionProperties = taskKey[Seq[File]]("Makes a version.properties file.")

gitHeadCommitSha in ThisBuild := Process("git rev-parse HEAD").lines.head

def PreownedKittenProject(name: String): Project = (
  Project(name, file(name)).
    settings(
      version := "1.0",
      organization := "com.preowned-kittens",
      libraryDependencies += "org.specs2" % "specs2_2.10" % "1.14" % "test"
    )
)

lazy val common = (
  PreownedKittenProject("common").
  settings(
    makeVersionProperties := {
      val propFile = (resourceManaged in Compile).value / "version.properties"
      val content = "version=%s" format (gitHeadCommitSha.value)
      IO.write(propFile, content)
      Seq(propFile)
    }
  )
)

lazy val analytics = (
  PreownedKittenProject("analytics").
    dependsOn(common).
    settings()
)

lazy val website = (
  PreownedKittenProject("website").
    dependsOn(common).
    settings()
)
