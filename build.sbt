name := "Earth-205-aiyoshizawa"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  filters,
"mysql" % "mysql-connector-java" % "5.1.41",
"org.mindrot" % "jbcrypt" % "0.4"
)     

play.Project.playJavaSettings
