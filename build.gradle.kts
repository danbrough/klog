import BuildVersion.buildVersionName
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
  id("org.jetbrains.dokka")
}


version = buildVersionName
group = projectGroup


buildscript {
  repositories {
    mavenCentral()
    gradlePluginPortal()
  }
}

repositories {
  mavenCentral()
  google()
}

kotlin {

  jvm()
  js {
    nodejs()
  }

  android {
    publishLibraryVariants("release")
  }

  linuxX64()

  macosX64()
  macosArm64()
  mingwX64()


  sourceSets {
    val commonMain by getting {}

    val commonTest by getting {
      dependencies {
        implementation(kotlin("test"))
      }
    }

    val jvmCommonMain by creating {
      dependsOn(commonMain)
    }

    val jvmCommonTest by creating {
      dependsOn(commonTest)
    }

    val jvmMain by getting {
      dependsOn(jvmCommonMain)
    }

    val androidMain by getting {
      dependsOn(jvmCommonMain)
    }
    //  val androidAndroidTestRelease by getting

    val androidTest by getting {
      dependsOn(jvmCommonTest)
//      dependsOn(androidAndroidTestRelease)

    }


    val androidAndroidTest by getting {
      dependsOn(jvmCommonTest)

      dependencies {
        implementation(AndroidX.test.runner)
        implementation(AndroidX.test.ext.junit.ktx)
      }
    }

  }

  val posixMain by sourceSets.creating {}

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

/*TODO fix
        compilations["test"].defaultSourceSet {
      dependsOn(sourceSets["posixTest"])
    }*/

  }

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
    jvmTarget = "11"
  }
}


tasks.dokkaHtml.configure {
  outputDirectory.set(buildDir.resolve("dokka"))
}


val javadocJar by tasks.registering(Jar::class) {
  archiveClassifier.set("javadoc")
  from(tasks.dokkaHtml)
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
    maven(project.buildDir.resolve("m2").toURI()) {
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

  publications.all {
    if (this !is MavenPublication) return@all

    artifact(javadocJar)

    pom {

      name.set("KLog")
      description.set("Kotlin multiplatform logging implementation")
      url.set("https://github.com/danbrough/klog/")


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

      issueManagement {
        system.set("GitHub")
        url.set("https://github.com/danbrough/klog/issues")
      }

      developers {
        developer {
          id.set("danbrough")
          name.set("Dan Brough")
          email.set("dan@danbrough.org")
          organizationUrl.set("https://danbrough.org")
        }
      }
    }

  }


}

android {
  compileSdk = ProjectProperties.SDK_VERSION
  sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
  namespace = projectGroup


  defaultConfig {
    minSdk = 23
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }

  signingConfigs.register("release") {
    storeFile = File(System.getProperty("user.home"), ".android/keystore")
    keyAlias = "klog"
    storePassword = System.getenv("KEYSTORE_PASSWORD") ?: ""
    keyPassword = System.getenv("KEYSTORE_PASSWORD") ?: ""
  }

  lint {
    abortOnError = false
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