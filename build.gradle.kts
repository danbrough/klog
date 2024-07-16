@file:OptIn(ExperimentalKotlinGradlePluginApi::class)


import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

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


