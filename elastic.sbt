import AssemblyKeys._

name := "Elastic Search Index Project"

version := "0.0.1"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  ("org.apache.spark" %% "spark-streaming" % "1.1.0" % "provided"),
  ("org.apache.spark" %% "spark-core" % "1.1.0" % "provided"),
  ("org.apache.spark" %% "spark-streaming-kafka" % "1.1.0")
)

assemblySettings

jarName in assembly := "elastic-assembly.jar"

mergeStrategy in assembly := {
  case m if m.toLowerCase.endsWith("manifest.mf") =>
    MergeStrategy.discard
  case m if m.toLowerCase.matches("meta-inf.*\\.sf$") =>
    MergeStrategy.discard
  case "log4j.properties" =>
    MergeStrategy.discard
  case m if m.toLowerCase.startsWith("meta-inf/services/") =>
    MergeStrategy.filterDistinctLines
  case "reference.conf" =>
    MergeStrategy.concat
  case _  =>
    MergeStrategy.first
}

assemblyOption in assembly ~= { _.copy(cacheOutput = false) }
