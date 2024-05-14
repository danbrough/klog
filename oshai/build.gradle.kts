@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.danbrough.klog.support.Constants
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget
import org.jetbrains.kotlin.konan.target.HostManager

plugins {
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.android.library)
  id("org.danbrough.klog.support")
  `maven-publish`
  signing
}

group = Constants.KLOG_PACKAGE
version = "0.0.3-beta01"

repositories {
  mavenCentral()
  google()
}

kotlin {
  applyDefaultHierarchyTemplate()

  mingwX64()
  linuxX64()
  linuxArm64()
  androidNativeX64()
  androidNativeArm64()


  if (HostManager.hostIsMac) {
    macosArm64()
    macosX64()
    iosArm64()
    iosX64()
  }

  jvm {
    compilerOptions {
      jvmTarget = Constants.JVM_TARGET
    }
  }

  androidTarget {
    publishLibraryVariants("release")

    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    compilerOptions {
      jvmTarget = Constants.JVM_TARGET
    }
  }

  compilerOptions {
    languageVersion = KotlinVersion.KOTLIN_1_9
    apiVersion = KotlinVersion.KOTLIN_1_9
    freeCompilerArgs = listOf("-Xexpect-actual-classes")
  }

  sourceSets {

    val commonMain by getting {
      dependencies {
        implementation(kotlin("reflect"))
        implementation(project(":api"))
        implementation(libs.oshai.logging)
      }
    }

    commonTest {
      dependencies {
        implementation(kotlin("test"))
      }
    }

    val jvmAndroidMain by creating {
      dependsOn(commonMain)
      dependencies {
        implementation(libs.logback.classic)
      }
    }

    androidMain {
      dependsOn(jvmAndroidMain)
    }


    jvmMain {
      dependsOn(jvmAndroidMain)
    }

    val androidInstrumentedTest by getting {
      dependencies {
        implementation(kotlin("test-junit"))
        implementation(libs.androidx.test.runner)
      }
    }

    nativeMain {
      dependencies {
      }
    }
  }


  targets.withType<KotlinJvmTarget> {
    mainRun {
      mainClass = "klog.TestApp"
      classpath(compilations["test"])
    }
  }
}



android {
  compileSdk = Constants.Android.COMPILE_SDK
  namespace = project.group.toString()

  defaultConfig {
    minSdk = Constants.Android.MIN_SDK
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  compileOptions {
    sourceCompatibility = Constants.JAVA_VERSION
    targetCompatibility = Constants.JAVA_VERSION
  }
}

