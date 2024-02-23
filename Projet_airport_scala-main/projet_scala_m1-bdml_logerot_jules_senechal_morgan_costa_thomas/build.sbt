val scala3Version = "3.3.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "projet_scala_m1-bdml_logerot_jules_senechal_morgan_costa_thomas",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % "0.7.29" % Test,
      "org.xerial" % "sqlite-jdbc" % "3.34.0"
    )
  )
