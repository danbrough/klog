pluginManagement {

  repositories {
    maven("https://s01.oss.sonatype.org/content/groups/staging/")
    gradlePluginPortal()
    mavenCentral()
    google()
  }
}



plugins {
  id("de.fayard.refreshVersions") version "0.60.2"
}


rootProject.name = "klog"



