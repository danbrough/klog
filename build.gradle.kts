@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.danbrough.klog.support.Constants
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
  alias(libs.plugins.kotlin.multiplatform) apply false
  alias(libs.plugins.android.library) apply false
  id("org.danbrough.klog.support")
  alias(libs.plugins.kotlin.jvm) apply false
  signing
}

group = Constants.KLOG_PACKAGE
version = "0.0.3-beta01"

repositories {
  mavenCentral()
  google()
}

allprojects {
  tasks.withType<AbstractTestTask> {
    if (this is Test) {
      useJUnitPlatform()
    }

    testLogging {
      events = setOf(
        TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED
      )
      showStandardStreams = true
      showStackTraces = true
      exceptionFormat = TestExceptionFormat.FULL
    }

    outputs.upToDateWhen {
      false
    }
  }

  pluginManager.apply("maven-publish")
  pluginManager.apply("signing")
  extensions.findByType<PublishingExtension>()?.apply {


    repositories {
      maven(rootProject.layout.buildDirectory.asFile.get().resolve("m2")) {
        name = "Local"
      }
    }

    extensions.findByType<SigningExtension>()?.apply {
      publications.all {
        sign(this)
      }
    }

  }

}

/*
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
        implementation(libs.slf4j.api)
      }
    }

    androidMain {
      dependsOn(jvmAndroidMain)
    }

    val androidInstrumentedTest by getting {
      dependencies {
        implementation(kotlin("test-junit"))
        implementation(libs.androidx.test.runner)
      }
    }

    jvmMain {
      dependsOn(jvmAndroidMain)
    }

    jvmTest {
      dependencies {
        implementation(libs.logback.classic)
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

publishing {
  repositories {
    maven(rootProject.layout.buildDirectory.asFile.get().resolve("m2")) {
      name = "Local"
    }
  }

  signing {
    publications.all {
      sign(this)
    }
  }
}


tasks.withType<AbstractTestTask> {
  if (this is Test) {
    useJUnitPlatform()
  }

  testLogging {
    events = setOf(
      TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED
    )
    showStandardStreams = true
    showStackTraces = true
    exceptionFormat = TestExceptionFormat.FULL
  }

  outputs.upToDateWhen {
    false
  }
}

*/
