import sbt._
import Keys._

object TemplateBuild extends Build {
  import Dependencies._

  lazy val root =
    Project("root", file("."))
      .aggregate(templateApi, templates, sbtPlugin)

  lazy val templateApi =
    Project("template-api", file("template-api"))
      .settings(
        libraryDependencies += commonsLang
      )

  lazy val templates =
    Project("templates", file("templates"))
      .settings(
        libraryDependencies ++= Seq(
          scalaIO,
          Test.specs
        ),
        libraryDependencies <+= scalaVersion(scalaCompiler)
      )
      .dependsOn(templateApi % "test")

  lazy val sbtPlugin =
    Project("sbt-plugin", file("sbt-plugin"))
}

object Dependencies {
  val commonsLang = "commons-lang"                  %  "commons-lang"   % "2.6"
  val scalaIO     = "com.github.scala-incubator.io" %% "scala-io-file"  % "0.2.0"

  def scalaCompiler(version: String) =
                    "org.scala-lang"                %  "scala-compiler" %  version

  object Test {
    val specs     = "org.specs2"                    %%   "specs2"       %   "1.7.1"    %   "test"
  }
}