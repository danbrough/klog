import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinAndroidTarget
import org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget
import org.jetbrains.kotlin.konan.target.HostManager

plugins {
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.com.android.library)
  alias(libs.plugins.xtras)
  `maven-publish`
  //idea
}

group = "org.danbrough"
version = libs.versions.klog.get()

repositories {
  mavenCentral()
  google()
}


val javaVersion = JavaVersion.VERSION_1_8

kotlin {
  applyDefaultHierarchyTemplate()

  jvm {
    compilations.all {
      kotlinOptions.jvmTarget = javaVersion.toString()
    }
  }

  js {
    nodejs()
  }

  androidTarget {
    val t: KotlinJvmTarget
    val s: KotlinAndroidTarget
    publishLibraryVariants("release")
    compilations.all {
      kotlinOptions.jvmTarget = javaVersion.toString()
    }
  }

  if (HostManager.hostIsLinux) {
    linuxX64()
    linuxArm64()
    linuxX64()
    mingwX64()

  } else if (HostManager.hostIsMac) {
    macosArm64()
    macosX64()
    iosArm64()
    iosSimulatorArm64()
    iosX64()
    iosSimulatorArm64()
  }


  sourceSets {

    commonTest {
      dependencies {
        implementation(kotlin("test"))

      }
    }

    val commonMain by getting {
      dependencies {
        //implementation(libs.kotlinx.serialization.json)
        implementation(kotlin("stdlib"))
      }
    }

    val jvmAndroidMain by creating {
      dependsOn(commonMain)
    }

    val androidMain by getting {
      dependsOn(jvmAndroidMain)
    }

    val androidInstrumentedTest by getting {
      dependencies {
        implementation(libs.androidx.runner)
        implementation(libs.androidx.annotation)
      }
    }

    val jvmMain by getting {
      dependsOn(jvmAndroidMain)
    }

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

android {
  compileSdk = 34
  namespace = project.group.toString()
  defaultConfig {
    minSdk = 22
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  compileOptions {
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
  }
}




publishing.publications.all {
  if (this is MavenPublication) {
    pom {

      name.set("KLog")
      description.set("Kotlin MPP logging library")

      url.set("https://github.com/danbrough/xtras/")

      licenses {
        license {
          name.set("Apache-2.0")
          url.set("https://opensource.org/licenses/Apache-2.0")
        }
      }

      scm {
        connection.set("scm:git:git@github.com:danbrough/klog.git")
        developerConnection.set("scm:git:git@github.com:danbrough/klog.git")
        url.set("https://github.com/danbrough/klog/")
      }

      issueManagement {
        system.set("GitHub")
        url.set("https://github.com/danbrough/klog/issues")
      }

      developers {
        developer {
          id.set("danbrough")
          name.set("Dan Brough")
          email.set("dan@danbrough.org")
          organizationUrl.set("https://github.com/danbrough/klog")
        }
      }
    }
  }
}


