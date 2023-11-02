plugins {
  alias(libs.plugins.kotlin.multiplatform)
}

repositories {
  mavenCentral()
}

kotlin {
  applyDefaultHierarchyTemplate()

  linuxX64()
  macosArm64()
  macosX64()
  mingwX64()
  jvm()


  if (System.getProperty("idea.active") == null) {
    androidTarget()
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
