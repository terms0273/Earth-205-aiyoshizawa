name := "Earth-205-aiyoshizawa"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
"mysql" % "mysql-connector-java" % "5.1.41"
)     

play.Project.playJavaSettings
