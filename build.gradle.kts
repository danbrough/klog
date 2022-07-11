import Common_gradle.Common.message
import ProjectProperties.LOCAL_MAVEN_REPO
import ProjectProperties.projectGroup
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
  kotlin("multiplatform")
  id("com.android.library")
  `maven-publish`
  signing
}

buildscript {
  repositories {
    mavenCentral()
    gradlePluginPortal()
  }
}


//BuildVersion.init(project)

version = BuildVersion.buildVersionName
group = projectGroup


kotlin {

  jvm()

  android {
    publishLibraryVariants("release")
  }

  linuxX64()
  //macosX64()

  js {
    nodejs()
  }

  if (!ProjectProperties.IDE_ACTIVE) {
    linuxArm64()
    linuxArm32Hfp()

    mingwX64()
    //mingwX86()

    androidNativeArm32()
    androidNativeArm64()
    androidNativeX64()
    androidNativeX86()

    //iosArm32()
    iosArm64()
    //iosSimulatorArm64()
    iosX64()

    macosArm64()
    macosX64()

    tvosArm64()
    //tvosSimulatorArm64()
    tvosX64()

    //wasm()
    //wasm32()

    //watchosArm32()
    //watchosArm64()
    //watchosSimulatorArm64()
    //watchosX64()
    //watchosX86()


  }




  sourceSets {
    val commonMain by getting {
      dependencies {
        //  runtimeOnly(kotlin("reflect"))
        implementation(kotlin("stdlib"))
        // https://mvnrepository.com/artifact/io.ktor/ktor-utils
        //implementation(Ktor.utils)

      }
    }

    val commonTest by getting {
      dependencies {
        implementation(kotlin("test"))
        implementation(KotlinX.coroutines.core)
      }
    }

    val posixMain by creating {
      dependsOn(commonMain)
    }

    val posixTest by creating {
      dependsOn(commonTest)
    }

    val jvmCommonMain by creating {
      dependsOn(commonMain)
    }

    val androidMain by getting {
      dependsOn(jvmCommonMain)
    }

    val androidAndroidTest by getting {
      dependencies {
        implementation(AndroidX.test.runner)
        implementation(AndroidX.test.ext.junit.ktx)
      }
    }

    val androidTest by getting {
      dependsOn(commonTest)
    }

    val jvmMain by getting {
      dependsOn(jvmCommonMain)
    }
  }



  targets.withType(KotlinNativeTarget::class).all {

    //println("NATIVE-TARGET: $name : apple:${this.konanTarget.family.isAppleFamily} linux:${this.konanTarget.family}")

    compilations["main"].apply {

      cinterops.create("klog") {
        packageName("org.danbrough.klog.posix")
        defFile(project.file("src/posixMain/klog.def"))
      }

      defaultSourceSet {
        dependsOn(sourceSets["posixMain"])
      }
    }

    compilations["test"].defaultSourceSet {
      dependsOn(sourceSets["posixTest"])
    }

  }
}

publishing {
  repositories {
    maven(LOCAL_MAVEN_REPO)

    maven {
      val releaseRepo = "https://oss.sonatype.org/service/local/staging/deploy/maven2/"
      val snapshotRepo = "https://oss.sonatype.org/content/repositories/snapshots/"
      val isReleaseVersion = !version.toString().endsWith("-SNAPSHOT")

      setUrl(if (isReleaseVersion) releaseRepo else snapshotRepo)

      credentials {
        username = project.property("ossrhUsername")?.toString()
        password = project.property("ossrhPassword")?.toString()
      }

    }
  }


}


android {
  compileSdk = ProjectProperties.SDK_VERSION
  sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
  namespace = projectGroup

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
//version = BuildVersion.
//group = ProjectProperties.groupID


repositories {
  mavenCentral()
  google()
  maven("https://h1.danbrough.org/maven")
}


allprojects {

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





