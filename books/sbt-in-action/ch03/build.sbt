name := "preowned-kittens"
version := "1.0"
organization := "com.preowned-kittens"

libraryDependencies += "org.specs2" % "specs2_2.10" % "1.14" % "test"

val gitHeadCommitSha = taskKey[String]("Determines the current git commit SHA")
gitHeadCommitSha := Process("git rev-parse HEAD").lines.head


val makeVersionProperties = taskKey[Seq[File]]("Makes a version.properties file.")

makeVersionProperties := {
  val propFile = new File((resourceManaged in Compile).value, "version.properties")
	val content = "version=%s" format (gitHeadCommitSha.value)
  IO.write(propFile, content)
  Seq(propFile)
}

val taskA = taskKey[String]("taskA")
val taskB = taskKey[String]("taskB")
val taskC = taskKey[String]("taskC")
taskA := { val b = taskB.value; val c = taskC.value; "taskA" }
taskB := { Thread.sleep(5000); "taskB" }
taskC := { Thread.sleep(5000); "taskC" }


val taskD = taskKey[String]("taskC")
taskD := {
  val log = streams.value.log
  log.info("run taskD")  
  log.warn("run taskD")
  "taskD"
} 




//resourceGenerators in Compile += makePropertiesFile

/* // Cyclic reference involving
val taskX = taskKey[String]("taskX")
taskX := { val y = taskY.value; "taskX" }
val taskY= taskKey[String]("taskY")
taskY := { val x = taskX.value; "taskY" }
*/