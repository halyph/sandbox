name := "akka-playground"

version := "1.0"

scalaVersion in ThisBuild := "2.11.8"

lazy val helloAkka = project("hello-akka")
lazy val playingWithActors = project("playing-with-actors")

lazy val multiThreadingWithAkka = project("multi-akka-mark-lewis")
    .settings(
      libraryDependencies ++= Seq(
        "com.typesafe.akka" %% "akka-actor" % "2.4.0"
    )
)

def project(id: String) = Project(id, base = file(id))