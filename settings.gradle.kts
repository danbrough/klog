pluginManagement {
  repositories {
    mavenCentral()
    gradlePluginPortal()
    google()

    val xtrasMavenDir = if (extra.has("xtras.dir.maven")) File(extra["xtras.dir.maven"].toString())
    else if (extra.has("xtras.dir")) File(extra["xtras.dir"].toString()).resolve("maven")
    else error("Neither xtras.dir.maven or xtras.dir are set")
    logger.log(LogLevel.WARN, "xtrasMavenDir is $xtrasMavenDir")

    maven(xtrasMavenDir) {
      name = "Xtras"
    }

    /*
        val xtrasMavenDir =
      if (hasProperty("xtras.dir.maven")) File(property("xtras.dir.maven").toString())
      else if (hasProperty("xtras.dir")) File(property("xtras.dir").toString()).resolve("maven")
      else error("Neither xtras.dir.maven or xtras.dir are set")
    maven(xtrasMavenDir) {
      name = "Xtras"
    }
     */
  }
}

plugins {
  id("de.fayard.refreshVersions") version "0.60.4"
////                          # available:"0.60.5"
  id("org.gradle.toolchains.foojay-resolver-convention") version ("0.8.0")
}


rootProject.name = "klog"