pluginManagement {
  repositories {
    //maven("https://maven.danbrough.org")

    mavenCentral()
    google()
    gradlePluginPortal()
  }
}


plugins {
  id("de.fayard.refreshVersions") version "0.60.5"
}

rootProject.name = "klog"
includeBuild("../../xtras/plugin")

includeBuild("support")

include(":core", ":slf4j", ":oshai")
