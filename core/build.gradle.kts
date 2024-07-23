@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.danbrough.klog.support.Constants
import org.danbrough.klog.support.declareNativeTargets
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget

plugins {
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.android.library)
}


repositories {
  mavenCentral()
  google()
}


kotlin {
  declareNativeTargets()

  jvm {
    compilerOptions {
      jvmTarget = Constants.JVM_TARGET
    }
  }

  js {
    nodejs()
  }

  @OptIn(ExperimentalWasmDsl::class)
  wasmJs {
    nodejs()
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
      }
    }

    commonTest {
      dependencies {
        implementation(kotlin("test"))
      }
    }
    /*
        val jvmAndroidMain by creating {
          dependsOn(commonMain)
        }*/

    androidMain {
      //dependsOn(jvmAndroidMain)
    }


    jvmMain {
      //dependsOn(jvmAndroidMain)
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


/*
afterEvaluate {

  tasks.withType<AndroidUnitTest> {
    enabled = false
  }

  tasks.getByName("wasmJsTest") {
    enabled = false
  }

}*/
