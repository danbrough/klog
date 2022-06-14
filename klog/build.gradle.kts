import ProjectProperties.LOCAL_MAVEN_REPO
import BuildVersion.buildVersionName
import ProjectProperties.projectGroup

import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
  kotlin("multiplatform")
  id("com.android.library")
  `maven-publish`
}

version = buildVersionName
group = projectGroup

kotlin {

  val commonMain by sourceSets.getting

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

  val nativeTest by sourceSets.creating {
    dependsOn(commonTest)
  }


  jvm()
  linuxX64()
  android()

  if (!ProjectProperties.IDE_ACTIVE) {


    js {
      nodejs()
    }
    linuxArm64()
    linuxArm32Hfp()
    mingwX64()
  }

  targets.withType(KotlinNativeTarget::class).all {
    compilations["main"].defaultSourceSet {
      dependsOn(nativeMain)
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