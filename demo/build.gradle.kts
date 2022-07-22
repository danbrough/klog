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
  if (project.hasProperty("useStaging"))
    maven("https://s01.oss.sonatype.org/content/groups/staging/")
}


kotlin {
  jvm()


  android()
  val osName = System.getProperty("os.name")
  if (osName == "Linux") {
    linuxX64()
    linuxArm32Hfp()
    linuxArm64()

/*
    js {
      nodejs()
    }
*/

  } else if (osName.startsWith("Mac")) {
    macosArm64()
    macosX64()
  } else if (osName.startsWith("Windows")) {
    mingwX64()
  }


  sourceSets {
    commonMain {
      dependencies {
        implementation("org.danbrough:klog:_")
        implementation(KotlinX.coroutines.core)
      }
    }

    commonTest {
      dependencies {
        implementation(kotlin("test"))
      }
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


