import Google.android

plugins {
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.com.android.library)
}

repositories {
  mavenCentral()
}

kotlin {
  applyDefaultHierarchyTemplate()

  androidTarget()
  linuxX64()
  macosArm64()
  macosX64()
  mingwX64()
  jvm()


  if (System.getProperty("idea.active") == null) {

    iosX64()
    iosArm64()
    watchosArm64()
    watchosX64()
    androidNativeX86()
    androidNativeX64()
    androidNativeArm64()
  }

  sourceSets {
    commonTest {
      dependencies {
        implementation(kotlin("test"))
      }
    }
  }
}


android {
  compileSdk= 34

  namespace = "thang"
}