@file:OptIn(ExperimentalKotlinGradlePluginApi::class)


import org.danbrough.xtras.TaskNames
import org.danbrough.xtras.xError
import org.danbrough.xtras.xInfo
import org.danbrough.xtras.xWarn
import org.danbrough.xtras.xtrasDir
import org.danbrough.xtras.xtrasMavenDir
import org.danbrough.xtras.xtrasPublishing
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.konan.target.HostManager
import shadow.bundletool.com.android.tools.r8.internal.xE

plugins {
  alias(libs.plugins.kotlin.multiplatform) apply false
  //alias(libs.plugins.android.library) apply false
  alias(libs.plugins.shadow) apply false
  alias(libs.plugins.xtras)
//  id("org.danbrough.klog.support")
  alias(libs.plugins.kotlin.jvm) apply false
  alias(libs.plugins.kotlin.android.library) apply false
  alias(libs.plugins.dokka)
  `maven-publish`
  signing
}


repositories {
  mavenCentral()
  google()
}


subprojects {


  afterEvaluate {

    xInfo("project: $name")
    if (name != "test")
      xtrasPublishing()

    xInfo("xtras maven dir: $xtrasMavenDir")
    //logWarn("kotlin extension $kotlinExtension ${kotlinExtension::class.java}")*/

    pluginManager.apply("maven-publish")
    pluginManager.apply("signing")

    extensions.findByType<JavaPluginExtension>()?.apply {
      sourceCompatibility = JavaVersion.VERSION_11
      targetCompatibility = JavaVersion.VERSION_11
    }

    extensions.findByType<KotlinMultiplatformExtension>()?.apply {

      //applyDefaultHierarchyTemplate()

      mingwX64()
      linuxX64()
      linuxArm64()

      androidNativeX64()
      androidNativeArm64()
      //androidNativeArm32()

      if (HostManager.hostIsMac) {
        macosArm64()
        macosX64()
        iosArm64()
        iosX64()
        iosSimulatorArm64()
        watchosArm64()
        tvosArm64()
      }
    }

    tasks.withType<AbstractTestTask> {

      if (this is Test) {
        useJUnitPlatform()
      }

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
}

/*
afterEvaluate {



  val mavenDir = project.xtrasMavenDir
    val deleteMavenTask = tasks.register("deleteMavenDir") {
      doFirst {
        xWarn("deleting $mavenDir!")
        mavenDir.deleteRecursively()
      }
    }



      tasks.register<Exec>("publishToXtras") {
        dependsOn(deleteMavenTask)
        group = TaskNames.XTRAS_TASK_GROUP
        dependsOn(rootProject.childProjects.values.flatMap { it.tasks.filter { task -> task.name == "publishAllPublicationsToXtrasRepository" } })
        workingDir(mavenDir)
        commandLine("rsync", "-avHSx", "./", "maven:~/m2/")
      }



}
 */
