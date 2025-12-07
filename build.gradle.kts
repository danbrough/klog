@file:OptIn(ExperimentalKotlinGradlePluginApi::class)


import org.danbrough.klog.support.Constants
import org.danbrough.xtras.xError
import org.danbrough.xtras.xInfo
import org.danbrough.xtras.xtrasDir
import org.danbrough.xtras.xtrasMavenDir
import org.danbrough.xtras.xtrasPublishing
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.konan.target.HostManager

plugins {
  alias(libs.plugins.kotlin.multiplatform) apply false
  alias(libs.plugins.android.library) apply false
  //alias(libs.plugins.xtras)
  id("org.danbrough.xtras")
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

    xInfo("project: $name")
    xtrasPublishing()

    xInfo("xtras maven dir: $xtrasMavenDir")
    //logWarn("kotlin extension $kotlinExtension ${kotlinExtension::class.java}")*/

    pluginManager.apply("maven-publish")
    pluginManager.apply("signing")

    extensions.findByType<JavaPluginExtension>()?.apply {
      sourceCompatibility = Constants.JAVA_VERSION
      targetCompatibility = Constants.JAVA_VERSION
    }

    extensions.findByType<KotlinMultiplatformExtension>()?.apply {

      applyDefaultHierarchyTemplate()

      mingwX64()
      linuxX64()
      linuxArm64()

      androidNativeX64()
      androidNativeArm64()
      androidNativeArm32()

      if (HostManager.hostIsMac) {
        macosArm64()
        macosX64()
        iosArm64()
        iosX64()
        iosSimulatorArm64()
        watchosX64()
        watchosArm64()
        tvosX64()
        tvosArm64()
      }
    }
  }
}