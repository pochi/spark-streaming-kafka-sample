import AssemblyKeys._

name := "Elastic Search Index Project"

version := "0.0.1"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  ("org.apache.spark" %% "spark-streaming" % "1.1.0" % "provided"),
  ("org.apache.spark" %% "spark-core" % "1.1.0" % "provided"),
  ("org.apache.spark" %% "spark-streaming-kafka" % "1.1.0"),
  ("org.elasticsearch" % "elasticsearch-hadoop" % "2.1.0.Beta3")
)

resolvers ++= Seq(
   "JBoss Repository" at "http://repository.jboss.org/nexus/content/repositories/releases/",
   "Spray Repository" at "http://repo.spray.cc/",
   "Cloudera Repository" at "https://repository.cloudera.com/artifactory/cloudera-repos/",
   "Akka Repository" at "http://repo.akka.io/releases/",
   "Twitter4J Repository" at "http://twitter4j.org/maven2/",
   "Apache HBase" at "https://repository.apache.org/content/repositories/releases",
   "Twitter Maven Repo" at "http://maven.twttr.com/",
   "scala-tools" at "https://oss.sonatype.org/content/groups/scala-tools",
  "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
  "Second Typesafe repo" at "http://repo.typesafe.com/typesafe/maven-releases/",
  "sonatype-oss" at "http://oss.sonatype.org/content/repositories/snapshots",
  "conjars.org" at "http://conjars.org/repo",
  "clojars.org" at "http://clojars.org/repo"
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
