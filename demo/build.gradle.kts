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
  maven("https://s01.oss.sonatype.org/content/repositories/snapshots")
  maven("https://h1.danbrough.org/maven")
}


kotlin {
  jvm()
  android()
  linuxX64()

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



