pluginManagement {
  repositories {
    //maven("https://maven.danbrough.org")
    maven("https://s01.oss.sonatype.org/content/groups/staging/")
    mavenCentral()
    google()
    gradlePluginPortal()
  }
}
includeBuild("../../xtras/plugin")


plugins {
  id("de.fayard.refreshVersions") version "0.60.5"
  //id("org.danbrough.xtras")
}

rootProject.name = "klog"

includeBuild("support")

include(":core", ":slf4j", ":oshai")
