pluginManagement {
  repositories {
    mavenLocal()
    maven("https://s01.oss.sonatype.org/content/groups/staging/")
    mavenCentral()
    google()
    gradlePluginPortal()
  }
}

dependencyResolutionManagement {
  @Suppress("UnstableApiUsage")
  repositories {
    /*  google {
        mavenContent {
          includeGroupAndSubgroups("org.jetbrains")
          includeGroupAndSubgroups("androidx")
          includeGroupAndSubgroups("com.android")
          includeGroupAndSubgroups("com.google")
        }
      }*/

    mavenLocal()
    mavenCentral()
    //maven("https://maven.danbrough.org")

  }
}


plugins {
  id("de.fayard.refreshVersions") version "0.60.6"
}
includeBuild("support")
includeBuild("../xtras/plugin")


rootProject.name = "klog"


include(":core", ":slf4j", ":oshai")
