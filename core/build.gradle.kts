@file:OptIn(ExperimentalKotlinGradlePluginApi::class, ExperimentalWasmDsl::class)

import com.android.tools.r8.shaking.d8
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget

plugins {
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.kotlin.android.library)
  `maven-publish`
  signing
}


repositories {
  mavenCentral()
  google()
}


kotlin {

  applyDefaultHierarchyTemplate()

  android {
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    namespace = project.group.toString()

    minSdk = libs.versions.android.minSdk.get().toInt()


    /*defaultConfig {
      minSdk = Constants.Android.MIN_SDK
      testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }*/
  }

  wasmJs {
    nodejs()
    browser()
  }

  js {
    nodejs()
    browser()
  }

  jvm {}

  /*  js {
      nodejs()
    }

    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
    wasmJs {
      nodejs()
      browser()
    }*/

  /*  androidTarget {
      publishLibraryVariants("release")

      *//*@OptIn(ExperimentalKotlinGradlePluginApi::class)
    compilerOptions {
      jvmTarget = Constants.JVM_TARGET
    }*//*
  }*/

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


    /*    val androidInstrumentedTest by getting {
          dependencies {
            implementation(kotlin("test-junit"))
            implementation(libs.androidx.test.runner)
          }
        }*/

    val androidJvmMain by creating {
      dependsOn(commonMain)
    }

    jvmMain {
      dependsOn(androidJvmMain)
    }

    androidMain {
      dependsOn(androidJvmMain)
    }

    nativeMain {
      dependencies {}
    }

    wasmJsMain {
      dependencies {
        implementation(libs.kotlinx.browser)
      }
    }
  }


  targets.withType<KotlinJvmTarget> {
    mainRun {
      mainClass = "org.danbrough.klog.TestApp"
      classpath(compilations["test"])
    }
  }
}



