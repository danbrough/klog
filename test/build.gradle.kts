@file:OptIn(
  ExperimentalKotlinGradlePluginApi::class, InternalKotlinGradlePluginApi::class,
  ExperimentalWasmDsl::class, ExperimentalMainFunctionArgumentsDsl::class
)

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.InternalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalMainFunctionArgumentsDsl
import org.jetbrains.kotlin.konan.target.HostManager
import org.jetbrains.kotlin.konan.target.KonanTarget

plugins {
  alias(libs.plugins.kotlin.multiplatform)
  //alias(libs.plugins.kotlinx.serialization)
  //alias(libs.plugins.kmp.android.library)
  alias(libs.plugins.shadow)
}


kotlin {
  applyDefaultHierarchyTemplate()

  /*  android {
      namespace = "org.danbrough.krch"
      compileSdk = libs.versions.android.compileSdk.get().toInt()
      minSdk = libs.versions.android.minSdk.get().toInt()

      compilerOptions {
        jvmTarget.set(JvmTarget.JVM_11)
      }
    }*/

  linuxX64()
  linuxArm64()

  if (HostManager.hostIsMac) {
    macosX64()
    macosArm64()
  }

  js {
    binaries.executable()
    nodejs {
      passCliArgumentsToMainFunction()
    }
  }

  wasmJs {
    binaries.executable()
    nodejs {
      passCliArgumentsToMainFunction()
      //passProcessArgvToMainFunction()
    }
  }

  jvm {
    mainRun {
      mainClass = "org.danbrough.klog.test.JvmMain"
    }
  }

  sourceSets {
    commonMain.dependencies {
      implementation(projects.core)
      implementation(libs.kotlinx.coroutines)
    }
  }

}



afterEvaluate {

  kotlin.targets.withType<KotlinNativeTarget>().configureEach {

    binaries {
      executable("klogDemo", buildTypes = setOf(NativeBuildType.DEBUG)) {
        //compilation = compilations["test"]
        entryPoint = "org.danbrough.klog.test.main"
        //if (buildType == NativeBuildType.DEBUG && konanTarget == KonanTarget.LINUX_X64) linkerOpts += "--allow-multiple-definition"

        runTaskProvider?.configure {
          if (project.hasProperty("args")) args(
            project.property("args").toString().split(",").map { it.trim() })
          environment(*System.getenv().keys.filter { it.startsWith("KLOG_") }
            .map { it to System.getenv(it) }.toTypedArray())
        }
      }
    }

  }


  tasks.withType<ShadowJar> {
    mainClass = "org.danbrough.krch.test.JvmMain"
  }
}


