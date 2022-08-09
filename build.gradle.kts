@file:Suppress("UnstableApiUsage")

import BuildEnvironment.createNativeTargets
import BuildEnvironment.platformName
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.konan.target.KonanTarget

plugins {
  kotlin("multiplatform")
  id("com.android.library")
  `maven-publish`
  signing
  //id("io.codearte.nexus-staging")
  id("org.jetbrains.dokka")
  id("io.github.gradle-nexus.publish-plugin")

}


ProjectProperties.init(project)


version = ProjectProperties.buildVersionName
group = ProjectProperties.projectGroup

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

tasks.register("dude") {
  doLast {
    KonanTarget.predefinedTargets.forEach {
      println("KONAN TARGET: ${it.key}")
    }
    kotlin.presets.forEach {
      println("PRESET: ${it.name}")
    }
  }
}

kotlin {

  jvm()

  js {
    nodejs()
  }

  android {
    publishLibraryVariants("release")
  }

  createNativeTargets()

  val commonMain by sourceSets.getting

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

  val posixMain by sourceSets.creating {
    dependsOn(commonMain)
  }

  targets.withType(KotlinNativeTarget::class).all {

    compilations["main"].apply {

      cinterops.create("klog") {
        packageName("klog.posix")
        defFile(project.file("src/posixMain/klog.def"))
      }

      defaultSourceSet {
        dependsOn(posixMain)
      }
    }
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
    jvmTarget = ProjectProperties.KOTLIN_JVM_VERSION
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

//
//nexusStaging {
//  serverUrl =
//    "https://s01.oss.sonatype.org/service/local/" //required only for projects registered in Sonatype after 2021-02-24
//  packageGroup = "org.mycompany.myproject" //optional if packageGroup == project.getGroup()
//
//
//  stagingProfileId =
//    "yourStagingProfileId" //when not defined will be got from server using "packageGroup"
//
//
//  packageGroup = ProjectProperties.projectGroup
//  serverUrl = "https://s01.oss.sonatype.org/service/local/"
//
//}


nexusPublishing {
  repositories {
    sonatype {
      nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
      snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
      stagingProfileId.set("98edb69227dc82")
    }

  }
}



publishing {

  repositories {
    maven(ProjectProperties.LOCAL_M2) {
      name = "m2"
    }

/*    maven {
      name = "oss"

      val isReleaseVersion = !version.toString().endsWith("-SNAPSHOT")
      val mavenUrl =
        if (isReleaseVersion)
          "https://oss.sonatype.org/service/local/staging/deployByRepositoryId/orgdanbrough-1027"
        //"https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
        else "https://s01.oss.sonatype.org/content/repositories/snapshots/"

      setUrl(mavenUrl)

      credentials {
        username = project.property("ossrhUsername")!!.toString().trim()
        password = project.property("ossrhPassword")!!.toString().trim()
      }
    }*/
  }

  publications.all {
    if (this !is MavenPublication) return@all

    if (project.hasProperty("publishDocs")) artifact(javadocJar)

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

/*if (project.hasProperty("signPublications")) {
  signing {
    publishing.publications.all {
      sign(this)
    }
  }
}*/

signing {
  publishing.publications.all {
    sign(this)
  }
}

android {

  compileSdk = ProjectProperties.SDK_VERSION
  sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
  namespace = project.group.toString()


  defaultConfig {
    minSdk = ProjectProperties.MIN_SDK_VERSION
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  compileOptions {
    sourceCompatibility = ProjectProperties.JAVA_VERSION
    targetCompatibility = ProjectProperties.JAVA_VERSION
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


tasks.create("publishMacTargetsToSonatypeRepository") {
  kotlin.targets.withType<KotlinNativeTarget>().filter { it.konanTarget.family.isAppleFamily }
    .map { it.konanTarget.platformName }.forEach {
      dependsOn("publish${it.capitalize()}PublicationToSonatypeRepository")
    }
}


