import ProjectProperties.LOCAL_MAVEN_REPO
import ProjectProperties.projectGroup
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("multiplatform")
  id("com.android.library")
  `maven-publish`
}

buildscript {
  repositories {
    mavenCentral()
    gradlePluginPortal()
  }
}


BuildVersion.init(project)


version = BuildVersion.buildVersionName
group = projectGroup


kotlin {

  jvm()
  android()
  macosX64()
  linuxX64()

  js {
    nodejs()
  }

  if (!ProjectProperties.IDE_ACTIVE) {

    androidNativeArm32()
    androidNativeArm64()
    androidNativeX64()
    androidNativeX86()

    //iosArm32()
    iosArm64()
    iosSimulatorArm64()
    iosX64()

    macosArm64()

    linuxArm64()
    linuxArm32Hfp()

    mingwX64()
    //mingwX86()

    tvosArm64()
    tvosSimulatorArm64()
    tvosX64()

    //wasm()
    //wasm32()

    //watchosArm32()
    //watchosArm64()
    //watchosSimulatorArm64()
    //watchosX64()
    //watchosX86()

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

  val posixMain by sourceSets.creating {
    dependsOn(commonMain)
  }

  val posixTest by sourceSets.creating {
    dependsOn(commonTest)
  }


  sourceSets {

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



  targets.withType(org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget::class).all {


    compilations["main"].apply {
      cinterops.create("klog") {
        packageName("org.danbrough.klog.posix")
        defFile(project.file("src/posixMain/klog.def"))
      }

      defaultSourceSet {
        dependsOn(posixMain)
      }
    }

    compilations["test"].defaultSourceSet {
      dependsOn(posixTest)
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
  //maven("https://h1.danbrough.org/maven")
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





