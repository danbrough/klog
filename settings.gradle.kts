pluginManagement {
  repositories {
    mavenCentral()
    maven("/home/dan/workspace/xtras2/xtras/maven/")
    gradlePluginPortal()
    google()
  }
}

plugins {
  id("de.fayard.refreshVersions") version "0.60.3"
  id("org.gradle.toolchains.foojay-resolver-convention") version ("0.7.0")
////                                                  # available:"0.8.0")
}


rootProject.name = "klog"