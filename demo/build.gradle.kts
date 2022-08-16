import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
  kotlin("multiplatform")
  id("com.android.application")
}

buildscript {
  repositories {
    mavenCentral()
    gradlePluginPortal()
  }
}

repositories {
  mavenCentral()
  google()
  //for the latest snapshot versions
  maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
  //shouldn't need this, unless it hasn't reached maven central
  maven("https://s01.oss.sonatype.org/content/groups/staging/")
}


kotlin {
  jvm()
  android()
  js {
    nodejs()
  }

  val osName = System.getProperty("os.name")
  if (osName == "Linux") {
    linuxX64()
  } else if (osName.startsWith("Mac")) {
    macosArm64()
    macosX64()
  } else if (osName.startsWith("Windows")) {
    mingwX64()
  }


  sourceSets {
    val commonMain by getting {
      dependencies {
        implementation("org.danbrough:klog:_")
        implementation("org.danbrough.kotlinx:kotlinx-coroutines-core:_")
      }
    }

    commonTest {
      dependencies {
        implementation(kotlin("test"))
      }
    }

    val linuxX64Main by getting {
      dependsOn(commonMain)
    }
  }


}

android {
  compileSdk = 32
  sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
  namespace = "org.danbrough.klog.demo"

  defaultConfig {
    minSdk = 23
    targetSdk = 32
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }

}

tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class) {
  kotlinOptions {
    jvmTarget = "11"
  }
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


