pluginManagement {
  repositories {
    maven("https://s01.oss.sonatype.org/content/groups/staging/")
    mavenCentral()
    google()
    gradlePluginPortal()
  }
}


plugins {
  id("de.fayard.refreshVersions") version "0.60.6"
}
includeBuild("support")
//includeBuild("../../xtras/plugin")


rootProject.name = "klog"


include(":core", ":slf4j", ":oshai")
