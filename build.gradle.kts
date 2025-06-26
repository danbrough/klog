@file:OptIn(ExperimentalKotlinGradlePluginApi::class)


import org.danbrough.klog.support.Constants
import org.danbrough.xtras.logInfo
import org.danbrough.xtras.logWarn
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmExtension
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

plugins {
  alias(libs.plugins.kotlin.multiplatform) apply false
  alias(libs.plugins.android.library) apply false
  alias(libs.plugins.xtras)
  id("org.danbrough.klog.support")
  alias(libs.plugins.kotlin.jvm) apply false
  alias(libs.plugins.dokka)
  `maven-publish`
  signing
}


repositories {
  mavenCentral()
  google()
}



subprojects {
  afterEvaluate {
    logInfo("project: $name")
    logWarn("kotlin extension $kotlinExtension ${kotlinExtension::class.java}")
    extensions.findByType<JavaPluginExtension>()?.apply {
      //this.targetCompatibility =
      sourceCompatibility = Constants.JAVA_VERSION
      targetCompatibility = Constants.JAVA_VERSION
    }
  }
}