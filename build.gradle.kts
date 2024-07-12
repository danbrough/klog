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

val projectVersion = "0.0.3-beta01"

repositories {
  mavenCentral()
  google()
}

allprojects {
  group = Constants.KLOG_PACKAGE
  version = projectVersion

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
//  pluginManager.apply("signing")
  extensions.findByType<PublishingExtension>()?.apply {
    repositories {
      maven(rootProject.layout.buildDirectory.asFile.get().resolve("m2")) {
        name = "Local"
      }

      maven("https://maven.pkg.github.com/danbrough/klog") {
        name = "GitHubPackages"

        credentials {
          username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_USERNAME")
          password = project.findProperty("gpr.token") as String? ?: System.getenv("GITHUB_TOKEN")
        }
      }
    }

    /*    extensions.findByType<SigningExtension>()?.apply {
          publications.all {
            sign(this)
          }
        }*/
  }
}
