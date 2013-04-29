import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "vadhander"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaJdbc,
    javaEbean,
    //"org.hibernate" % "hibernate-annotations" % "3.5.6-Final",
    //"com.vividsolutions" % "jts" % "1.13",

    //"org.postgis" %  "postgis-main" % "1.3.3",
    //"org.postgis" % "postgis-jdbc" % "1.3.3",
    "postgresql" % "postgresql" % "9.1-901.jdbc4"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
  )

}
