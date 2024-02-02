import org.danbrough.xtras.sonatype.sonatypePublishing
import org.danbrough.xtras.xtrasPublishing
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.konan.target.HostManager

plugins {
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.com.android.library)
  alias(libs.plugins.xtras)
  `maven-publish`
  //idea
}

group = "org.danbrough.klog"
version = libs.versions.klog.get()

repositories {
  mavenCentral()
  google()
}


kotlin {
  applyDefaultHierarchyTemplate()
  jvm()

  js {
    nodejs()
  }

  androidTarget {
    publishLibraryVariants("release")
  }

  if (HostManager.hostIsLinux) {
    linuxX64()
    linuxArm64()
    linuxX64()
    mingwX64()

  } else if (HostManager.hostIsMac) {
    macosArm64()
    macosX64()
    iosArm64()
    iosSimulatorArm64()
    iosX64()
    iosSimulatorArm64()
  }


  sourceSets {

    commonTest {
      dependencies {
        implementation(kotlin("test"))
      }
    }

    val commonMain by getting {
      dependencies {
        //implementation(libs.kotlinx.serialization.json)
      }
    }

    val jvmAndroidMain by creating {
      dependsOn(commonMain)
    }

    val androidMain by getting {
      dependsOn(jvmAndroidMain)
    }

    val androidInstrumentedTest by getting {
      dependencies {
        implementation(libs.androidx.runner)
        //implementation("androidx.annotation:annotation-experimental:1.1.0")
        implementation("androidx.annotation:annotation:1.7.1")/*
            androidTestImplementation "androidx.test:runner:$androidXTestVersion"
    androidTestImplementation "androidx.test:rules:$androidXTestVersion"

         */
      }
    }

    val jvmMain by getting {
      dependsOn(jvmAndroidMain)
    }

  }


}


tasks.withType<AbstractTestTask> {
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

android {
  compileSdk = 34
  namespace = project.group.toString()
  defaultConfig {
    minSdk = 22
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

}




xtrasPublishing()

sonatypePublishing {}