import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.com.android.library)
  //alias(libs.plugins.kotlin.serialization)
  idea
}

repositories {
  mavenCentral()
}

kotlin {
  applyDefaultHierarchyTemplate()
  jvm()
  androidTarget()
  linuxX64()

  /*linuxX64()
  macosArm64()
  macosX64()
  mingwX64()


  if (System.getProperty("idea.active") == null) {
    iosX64()
    iosArm64()
    watchosArm64()
    watchosX64()
    androidNativeX86()
    androidNativeX64()
    androidNativeArm64()
  }
*/
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

    val jvmMain by getting {
      dependsOn(jvmAndroidMain)
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


}


android {
  compileSdk = 34

  namespace = project.group.toString()
}

