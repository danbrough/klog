import BuildVersion.buildVersionTasks
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("multiplatform") apply false
  id("com.android.library") apply false
  id("com.android.application") apply false
  id("org.jetbrains.kotlin.android") apply false

}

buildscript {
  repositories {
    mavenCentral()
    gradlePluginPortal()
  }
}



allprojects {

  repositories {
    maven("https://h1.danbrough.org/maven")
    mavenCentral()
    google()
  }

  tasks.withType<AbstractTestTask>() {
    testLogging {
      events = setOf(
        TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED
      )
      exceptionFormat = TestExceptionFormat.FULL
      showStandardStreams = true
      showStackTraces = true
    }
    outputs.upToDateWhen {
      false
    }
  }

  tasks.withType(KotlinCompile::class) {
    kotlinOptions {
      jvmTarget = ProjectProperties.KOTLIN_JVM_VERSION
    }
  }


}



buildVersionTasks()