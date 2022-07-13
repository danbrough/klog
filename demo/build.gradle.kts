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

  js {
    moduleName = "klog_danbrough_demo"
    nodejs()
  }

  android()
  linuxX64()

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
  compileSdk = 31
  sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
  namespace = "org.danbrough.klog.demo"

  defaultConfig {
    minSdk = 32
    targetSdk = 31
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

  }

  compileOptions {
    sourceCompatibility = JavaVersio
    targetCompatibility = ProjectProperties.JAVA_VERSION
  }

}

tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class) {
  kotlinOptions {
    jvmTarget = ProjectProperties.KOTLIN_JVM_VERSION
  }
}



