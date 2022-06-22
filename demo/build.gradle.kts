plugins {
  kotlin("multiplatform")
  id("com.android.application")
  `maven-publish`
  signing
}

buildscript {
  repositories {
    mavenCentral()
    gradlePluginPortal()
  }
}

repositories {
  maven("https://h1.danbrough.org/maven")
  mavenCentral()
  google()
}


kotlin {
  jvm()
  android()

  sourceSets {
    commonMain {
      dependencies {
        implementation("org.danbrough:klog:_")
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
  compileSdk = ProjectProperties.SDK_VERSION
  sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
  namespace = "org.danbrough.klog.demo"

  defaultConfig {
    minSdk = ProjectProperties.MIN_SDK_VERSION
    targetSdk = ProjectProperties.SDK_VERSION
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

  }

  compileOptions {
    sourceCompatibility = ProjectProperties.JAVA_VERSION
    targetCompatibility = ProjectProperties.JAVA_VERSION
  }

}

tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class) {
  kotlinOptions {
    jvmTarget = ProjectProperties.KOTLIN_JVM_VERSION
  }
}



