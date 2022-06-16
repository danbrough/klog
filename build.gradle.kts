import BuildVersion.buildVersionTasks
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import ProjectProperties.LOCAL_MAVEN_REPO
import BuildVersion.buildVersionName
import ProjectProperties.projectGroup

plugins {
  kotlin("multiplatform")
  id("com.android.library")
  `maven-publish`
 // id("com.android.application") apply false
 // id("org.jetbrains.kotlin.android") apply false

}

buildscript {
  repositories {
    mavenCentral()
    gradlePluginPortal()
  }
}

version = buildVersionName
group = projectGroup


kotlin {

  jvm()
  linuxX64()
  android()
  js {
    nodejs()
  }

  if (!ProjectProperties.IDE_ACTIVE) {
    linuxArm64()
    linuxArm32Hfp()
    mingwX64()
  }


  val commonMain by sourceSets.getting {
    dependencies {
      //  runtimeOnly(kotlin("reflect"))
      implementation(kotlin("stdlib"))
    }
  }

  val commonTest by sourceSets.getting {
    dependencies {
      implementation(kotlin("test"))
    }
  }

  val nativeMain by sourceSets.creating {
    dependsOn(commonMain)
    dependencies {
      implementation(KotlinX.coroutines.core)
    }
  }


  val jvmCommonMain by sourceSets.creating {
    dependsOn(commonMain)
  }

  val androidMain by sourceSets.getting {
    dependsOn(jvmCommonMain)
  }

  val jvmMain by sourceSets.getting {
    dependsOn(jvmCommonMain)
  }

  val nativeTest by sourceSets.creating {
    dependsOn(commonTest)
  }


  targets.withType(org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget::class).all {


    compilations["main"].apply {
      cinterops.create("klog") {
        packageName("org.danbrough.klog.native")
        defFile(project.file("src/nativeMain/klog.def"))
      }

      defaultSourceSet {
        dependsOn(nativeMain)
      }
    }

    compilations["test"].defaultSourceSet {
      dependsOn(nativeTest)
    }
  }
}

publishing {
  repositories {
    maven(LOCAL_MAVEN_REPO)
  }
}

android {
  compileSdk = ProjectProperties.SDK_VERSION
  sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
  namespace = projectGroup

  defaultConfig {
    minSdk = ProjectProperties.MIN_SDK_VERSION
    targetSdk = ProjectProperties.SDK_VERSION
  }

  compileOptions {
    sourceCompatibility = ProjectProperties.JAVA_VERSION
    targetCompatibility = ProjectProperties.JAVA_VERSION
  }

}
//version = BuildVersion.
//group = ProjectProperties.groupID

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