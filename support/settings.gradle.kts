pluginManagement {
  repositories {
    //  maven("https://maven.danbrough.org")
    maven("https://s01.oss.sonatype.org/content/groups/staging/")

    mavenCentral()
    gradlePluginPortal()
  }
}


dependencyResolutionManagement {
  versionCatalogs {
    create("libs") {
      from(files("../gradle/libs.versions.toml"))
    }
  }
}

//includeBuild("../../../xtras/plugin")