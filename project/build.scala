import sbt._
import Keys._
import org.scalatra.sbt._
import org.scalatra.sbt.PluginKeys._
import com.mojolly.scalate.ScalatePlugin._
import ScalateKeys._
import sbtbuildinfo.Plugin._

object OpeneyesBuild extends Build {
  val Organization = "org.openeyes"
  val Name = "Openeyes"
  val Version = "0.1.4"
  val ScalaVersion = "2.10.4"
  val ScalatraVersion = "2.3.0"

  lazy val project = Project (
    "openeyes",
    file("."),
    settings = buildInfoSettings ++
      Seq(
        sourceGenerators in Compile <+= buildInfo,
        buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
        buildInfoPackage := "openeyesApi"
      ) ++ ScalatraPlugin.scalatraWithJRebel ++ scalateSettings ++ Seq(
      organization := Organization,
      name := Name,
      version := Version,
      scalaVersion := ScalaVersion,
      resolvers ++= Seq(
        Classpaths.typesafeReleases,
        "amateras-repo" at "http://amateras.sourceforge.jp/mvn/"
      ),
      libraryDependencies ++= Seq(
        "com.novus" %% "salat" % "1.9.8",
        "jp.sf.amateras" %% "scalatra-forms" % "0.1.0",
        "org.json4s"   %% "json4s-jackson" % "3.2.9",
        "org.json4s" %% "json4s-mongo" % "3.2.9",
        "org.scalatra" %% "scalatra" % ScalatraVersion,
        "org.scalatra" %% "scalatra-json" % ScalatraVersion,
        "org.scalatra" %% "scalatra-scalate" % ScalatraVersion,
        "org.scalatra" %% "scalatra-specs2" % ScalatraVersion % "test",
        "org.scalatra" %% "scalatra-swagger"  % ScalatraVersion,
        "com.typesafe.slick" %% "slick" % "2.1.0",
        "org.slf4j" % "slf4j-nop" % "1.6.4",
        "mysql" % "mysql-connector-java" % "5.1.32",
        "c3p0" % "c3p0" % "0.9.1.2",
        "ch.qos.logback" % "logback-classic" % "1.0.6" % "runtime",
        "org.eclipse.jetty" % "jetty-webapp" % "9.1.3.v20140225" % "container",
        "org.eclipse.jetty" % "jetty-plus" % "9.1.3.v20140225" % "container",
        "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016" % "container;provided;test" artifacts (Artifact("javax.servlet", "jar", "jar"))
      ),
      scalateTemplateConfig in Compile <<= (sourceDirectory in Compile){ base =>
        Seq(
          TemplateConfig(
            base / "webapp" / "WEB-INF" / "templates",
            Seq.empty,  /* default imports should be added here */
            Seq(
              Binding("context", "_root_.org.scalatra.scalate.ScalatraRenderContext", importMembers = true, isImplicit = true)
            ),  /* add extra bindings here */
            Some("templates")
          )
        )
      }
    )
  )
}
