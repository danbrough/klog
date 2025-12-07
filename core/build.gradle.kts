@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.danbrough.klog.support.Constants
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget

plugins {
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.android.library)
  `maven-publish`
  signing
}


repositories {
  mavenCentral()
  google()
}


kotlin {

  jvm {
  }

  js {
    nodejs()
  }

  @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
  wasmJs {
    nodejs()
    browser()
  }

  androidTarget {
    publishLibraryVariants("release")

    /*@OptIn(ExperimentalKotlinGradlePluginApi::class)
    compilerOptions {
      jvmTarget = Constants.JVM_TARGET
    }*/
  }

  compilerOptions {/*    languageVersion = KotlinVersion.KOTLIN_2_1
    apiVersion = KotlinVersion.KOTLIN_2_1*/
    freeCompilerArgs = listOf("-Xexpect-actual-classes")
  }

  sourceSets {

    val commonMain by getting {
      dependencies {
        implementation(kotlin("reflect"))
        implementation(libs.kotlinx.coroutines)
      }
    }

    commonTest {
      dependencies {
        implementation(kotlin("test"))
      }
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

    wasmJsMain {
      dependencies {
        implementation(libs.kotlinx.browser)
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
}

