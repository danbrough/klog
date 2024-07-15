@file:OptIn(ExperimentalKotlinGradlePluginApi::class)


import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
  alias(libs.plugins.kotlin.multiplatform) apply false
  alias(libs.plugins.android.library) apply false
  id("org.danbrough.xtras")
  id("org.danbrough.klog.support")
  alias(libs.plugins.kotlin.jvm) apply false
  alias(libs.plugins.dokka)
  `maven-publish`
}


repositories {
  mavenCentral()
  google()
}


/*
subprojects {


  xtrasTesting { }

  pluginManager.apply("org.jetbrains.dokka")
  pluginManager.apply("maven-publish")


  val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
    from(tasks.getByName("dokkaHtml"))
  }



  extensions.findByType<PublishingExtension>()!!.apply {
    repositories {
      maven("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/") {


        name = "Sonatype"
        credentials {
          username = project.property("sonatype.username")!!.toString()
          password = project.property("sonatype.password")!!.toString()
        }
      }

      maven("https://s01.oss.sonatype.org/content/repositories/snapshots/") {
        name = "Snapshots"
        credentials {
          username = project.property("sonatype.username")!!.toString()
          password = project.property("sonatype.password")!!.toString()
        }
      }
    }

    extensions.findByType<SigningExtension>()?.apply {

      val signingKey =
        findProperty("GPG_SIGNING_KEY")?.toString() ?: System.getenv("GPG_SIGNING_KEY")
      val signingPassword =
        findProperty("GPG_SIGNING_PASSWORD")?.toString()
          ?: System.getenv("GPG_SIGNING_PASSWORD")!!

      if (signingKey != null) {
        useInMemoryPgpKeys(
          signingKey.replace("\\n", "\n"),
          signingPassword
        )
      } else logger.info("pgp signing disabled as GPG_SIGNING_KEY not set")

//      afterEvaluate {
//        if (project.kotlinExtension is KotlinMultiplatformExtension) {
//          val kotlinMultiplatform by publications.getting(MavenPublication::class) {
//            artifact(javadocJar)
//          }
//        }
//      }


    }
  }
}



*/

tasks.register("printThang") {
  doFirst {
    println("thang=${findProperty("thang")}")
    println("thang.message=${findProperty("thang.message")}")
  }
}