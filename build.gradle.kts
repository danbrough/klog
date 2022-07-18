import Common_gradle.BuildVersion.buildVersionName
import Common_gradle.BuildVersion.message
import ProjectProperties.projectGroup
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
  kotlin("multiplatform")
  id("com.android.library")
  id("common")
  `maven-publish`
  signing
}

buildscript {
  repositories {
    mavenCentral()
    gradlePluginPortal()
  }
}

//buildVersionTasks()

version = buildVersionName
group = projectGroup





tasks.create("testTask") {
  doLast {
    println("MESSAGE: $message")

    val osName = System.getProperty("os.name")
    val osArch = System.getProperty("os.arch")

  }

}

kotlin {

  jvm()

  android {
    publishLibraryVariants("release")
  }

  linuxX64()
//macosX64()

  js {
    moduleName = "klog_danbrough"
    nodejs()
  }

/*  if (!ProjectProperties.IDE_ACTIVE) {
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


  }*/




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

    val jvmCommonTest by creating {
      dependsOn(commonTest)
    }

    val androidMain by getting {
      dependsOn(jvmCommonMain)
    }

    val androidTest by getting {
      dependsOn(jvmCommonTest)
    }

    val jvmMain by getting {
      dependsOn(jvmCommonMain)
    }

    val jvmTest by getting {
      dependsOn(jvmCommonTest)
    }

    val androidAndroidTest by getting {
      dependsOn(jvmCommonTest)

      dependencies {
        implementation(AndroidX.test.runner)
        implementation(AndroidX.test.ext.junit.ktx)
      }
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

object Meta {
  const val desc = "KLog - Logging for Kotlin"
  const val license = "Apache-2.0"
  const val licenseUrl = "https://opensource.org/licenses/Apache-2.0"
  const val githubRepo = "danbrough/klog"

  /*const val release = "https://s01.oss.sonatype.org/service/local/"
  const val snapshot = "https://s01.oss.sonatype.org/content/repositories/snapshots/"*/
  const val release = "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
  const val snapshot = "https://s01.oss.sonatype.org/content/repositories/snapshots/"
}


publishing {
  repositories {
//maven(LOCAL_MAVEN_REPO)

/*
    repository(url: "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/") {
    authentication(userName: ossrhUsername, password: ossrhPassword)
  }

    snapshotRepository(url: "https://s01.oss.sonatype.org/content/repositories/snapshots/") {
    authentication(userName: ossrhUsername, password: ossrhPassword)
  }
*/
    maven(
      project.properties["MAVEN_REPO"]?.toString() ?: project.buildDir.resolve("m2").toURI()
    ) {
      name = "m2"
    }

    maven {

      name = "oss"


      val isReleaseVersion = !version.toString().endsWith("-SNAPSHOT")
      val mavenUrl = if (isReleaseVersion) Meta.release else Meta.snapshot

      setUrl(mavenUrl)

      credentials {
        username = project.property("ossrhUsername")!!.toString().trim()
        password = project.property("ossrhPassword")!!.toString().trim()
      }
    }

  }


  publications["kotlinMultiplatform"].apply {
    this as MavenPublication



    pom {

      licenses {
        license {
          name.set(Meta.license)
          url.set(Meta.licenseUrl)
        }
      }

      scm {
        connection.set("scm:git:git@github.com:danbrough/klog.git")
        developerConnection.set("scm:git:git@github.com:danbrough/klog.git")
        url.set("https://github.com/danbrough/klog/")
      }

      developers {
        developer {
          id.set("danbrough")
          name.set("Dan Brough")
          email.set("dan@danbrough.org")
        }
      }
    }

  }
}


signing {
  publishing.publications.all {
    sign(this)
  }
// sign(publishing.publications["kotlinMultiplatform"])
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

  signingConfigs.register("release") {
    storeFile = file("/home/dan/.android/keystore")
    keyAlias = "klog"
    storePassword = System.getenv("KEYSTORE_PASSWORD") ?: ""
    keyPassword = System.getenv("KEYSTORE_PASSWORD") ?: ""
  }


  buildTypes {

    getByName("debug") {
//debuggable(true)
    }

    getByName("release") {
      isMinifyEnabled = true
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
      )
      signingConfig = signingConfigs.getByName("release")
    }
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





