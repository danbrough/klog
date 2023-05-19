@file:Suppress("UnstableApiUsage")


import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.konan.target.HostManager

plugins {
  id("com.android.library")
  kotlin("multiplatform")
  `maven-publish`
  signing
  id("org.jetbrains.dokka")
}


version = "0.0.2-beta03"
group = "org.danbrough"



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

  android {
    publishLibraryVariants("debug", "release")
  }

  if (HostManager.hostIsMac) {
    macosArm64()
    macosX64()
    iosArm64()
    iosX64()
    watchosArm64()
    watchosX64()
  } else {

    linuxX64()
    linuxArm64()
    linuxArm32Hfp()
    mingwX64()
    androidNativeArm32()
    androidNativeArm64()
    androidNativeX64()
    androidNativeX86()

    js {
      nodejs()
    }
  }

  val commonMain by sourceSets.getting {
    dependencies {
      //  implementation("com.github.Simplx-dev:kotlin-format:_")
    }
  }

  sourceSets {


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

    val jvmTest by getting {
      dependsOn(jvmCommonTest)
    }

    val androidMain by getting {
      dependsOn(jvmCommonMain)


    }

    val androidInstrumentedTest by getting {
      dependsOn(jvmCommonTest)

      dependencies {
        implementation(AndroidX.test.runner)
        implementation(AndroidX.test.ext.junit)
        implementation(AndroidX.test.ext.junit.ktx)

      }
    }

    //  val androidAndroidTestRelease by getting


    /*
       val androidTest by getting {
          dependsOn(jvmCommonTest)
    //      dependsOn(androidAndroidTestRelease)
        }
    */


    /*    val androidAndroidTest by getting {
          dependsOn(jvmCommonTest)

          dependencies {
            implementation(AndroidX.test.runner)
            implementation(AndroidX.test.ext.junit.ktx)
          }
        }*/

  }


  val posixMain by sourceSets.creating {
    dependsOn(commonMain)
  }

  val androidNativeMain by sourceSets.creating {
    dependsOn(posixMain)
  }


  targets.withType(KotlinNativeTarget::class).all {

    compilations["main"].apply {

      cinterops.create("klog") {
        packageName("klog.posix")
        defFile(project.file("src/klog.def"))
      }

      defaultSourceSet {
        if (konanTarget.family == org.jetbrains.kotlin.konan.target.Family.ANDROID)
          dependsOn(androidNativeMain)
        else
          dependsOn(posixMain)
      }
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



tasks.register<Delete>("deleteDocs") {
  setDelete(file("docs/api"))
}

tasks.register<Copy>("copyDocs") {
  dependsOn("deleteDocs")
  from(buildDir.resolve("dokka"))
  destinationDir = file("docs/api")
}


tasks.dokkaHtml.configure {
  outputDirectory.set(buildDir.resolve("dokka"))
  finalizedBy("copyDocs")
}

tasks.dokkaJekyll.configure {
  outputDirectory.set(buildDir.resolve("jekyll"))
}

val javadocJar by tasks.registering(Jar::class) {
  archiveClassifier.set("javadoc")
  from(tasks.dokkaHtml)
}

publishing {

  repositories {
    maven("/usr/local/kotlinxtras/build/xtras/maven") {
      name = "Xtras"
    }

    maven(
      "https://s01.oss.sonatype.org/service/local/staging/deployByRepositoryId/${
        System.getenv("SONATYPE_REPO_ID") ?: "Sonatype_repo_id_not_set"
      }"
    ) {
      name = "SonaType"
      credentials {
        username = System.getenv("SONATYPE_USER")
        password = System.getenv("SONATYPE_PASSWORD")
      }
    }

  }

  publications.all {
    if (this !is MavenPublication) return@all


    if (project.properties["publishDocs"] == "1")
      artifact(javadocJar)

    if (properties["signPublications"] == "1")
      signing.sign(this)


    pom {


      name.set("KLog")
      description.set("Kotlin multiplatform logging implementation")
      url.set("https://github.com/danbrough/klog/")


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
          organizationUrl.set("https://danbrough.org")
        }
      }
    }

  }
}

/*
if (properties.get("signPublications") == "1")
  signing {
    sign(publishing.publications)
  }
*/



android {

  compileSdk = 33
  sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
  namespace = project.group.toString()


  defaultConfig {
    minSdk = 24
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

  signingConfigs.register("release") {
    storeFile = File(System.getProperty("user.home"), ".android/keystore")
    keyAlias = "klog"
    storePassword = System.getenv("KEYSTORE_PASSWORD") ?: ""
    keyPassword = System.getenv("KEYSTORE_PASSWORD") ?: ""
  }

  lint {
    // abortOnError = false
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

afterEvaluate {

  val signingTasks = tasks.withType(Sign::class.java).map { it.name }

  tasks.withType(PublishToMavenRepository::class.java).all {
    this.mustRunAfter(signingTasks)
  }


}