name := "bundle-pricing"

version := "1.0"

scalaVersion := "2.11.8"

resolvers +=  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
resolvers +=  "Sonatype Releases" at "http://oss.sonatype.org/content/repositories/releases"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6" % "test"
