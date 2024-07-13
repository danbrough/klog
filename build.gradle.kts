@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.danbrough.klog.support.Constants
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
  alias(libs.plugins.kotlin.multiplatform) apply false
  alias(libs.plugins.android.library) apply false
  id("org.danbrough.klog.support")
  alias(libs.plugins.kotlin.jvm) apply false
  alias(libs.plugins.dokka) apply false
  //alias(libs.plugins.xtras) apply false
  signing
}

val projectVersion = "0.0.3-beta02"
group = Constants.KLOG_PACKAGE
version = projectVersion

repositories {
  mavenCentral()
  google()
}

subprojects {
  group = Constants.KLOG_PACKAGE
  version = projectVersion

  tasks.withType<AbstractTestTask> {
    if (this is Test) {
      useJUnitPlatform()
    }

    testLogging {
      events = setOf(
        TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED
      )
      showStandardStreams = true
      showStackTraces = true
      exceptionFormat = TestExceptionFormat.FULL
    }

    outputs.upToDateWhen {
      false
    }
  }

  pluginManager.apply("maven-publish")
  pluginManager.apply("signing")
  pluginManager.apply("org.jetbrains.dokka")

  val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
    from(tasks.getByName("dokkaHtml"))
  }

//  pluginManager.apply("org.danbrough.xtras.sonatype")
  extensions.findByType<PublishingExtension>()!!.apply {
    repositories {
      maven("/tmp/maven") {
        name = "Thang"
      }
      /*
            maven("https://maven.pkg.github.com/danbrough/klog") {
              name = "GitHubPackages"

              credentials {
                username = project.property("sonatype.username")!!.toString()
                password = project.property("sonatype.password")!!.toString()
              }
            }
      */

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

      publications.all {
        if (this is MavenPublication) {
          println("PUBLICATION: $name")
          //if (name in setOf("kotlinMultiplatform", "jvm"))
          artifact(javadocJar)

          pom {
            name.set("KLog")
            description.set("Kotlin logging facade")
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
                organizationUrl.set("https://github.com/danbrough/klog")
              }
            }
          }
        }
        sign(this)
      }

      
    }
  }
}




