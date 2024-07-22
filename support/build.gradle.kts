plugins {
  `kotlin-dsl`
  `java-gradle-plugin`
}

repositories {
  mavenCentral()
}

group = "org.danbrough.klog.support"

dependencies {
  implementation(libs.kotlin.gradle.plugin)
}

gradlePlugin {
  plugins {
    create("support") {
      id = group.toString()
      implementationClass = "$group.SupportPlugin"
      displayName = "Support plugin"
      description = "Kotlin multiplatform support plugin for klog"
    }
  }
}

