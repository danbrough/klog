import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
  `kotlin-dsl`
  `java-gradle-plugin`
}

repositories {
  mavenCentral()
}

group = "org.danbrough.klog.support"
java {
  sourceCompatibility = JavaVersion.VERSION_11
  targetCompatibility = JavaVersion.VERSION_11
  withSourcesJar()
//  withJavadocJar()
}

kotlin {
  compilerOptions {
    this.jvmTarget = JvmTarget.JVM_11
  }
}

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

