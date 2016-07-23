name := "akka-playground"

version := "1.0"

scalaVersion := "2.11.8"

lazy val helloAkka = project("hello-akka")

def project(id: String) = Project(id, base = file(id))