@file:OptIn(
  ExperimentalKotlinGradlePluginApi::class, ExperimentalWasmDsl::class, ExperimentalMainFunctionArgumentsDsl::class
)

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalMainFunctionArgumentsDsl
import org.jetbrains.kotlin.konan.target.KonanTarget

plugins {
  //alias(libs.plugins.kmp)
  //alias(libs.plugins.kotlinx.serialization)
  //alias(libs.plugins.kmp.android.library)
  /*alias(libs.plugins.compose.compiler)
  alias(libs.plugins.compose.hotreload)
  alias(libs.plugins.ksp)*/
  //alias(libs.plugins.compose.multiplatform)
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.compose.multiplatform)
  alias(libs.plugins.compose.compiler)

  // alias(libs.plugins.shadow)
}


kotlin {
  //applyDefaultHierarchyTemplate()


  js {
    binaries.executable()
    browser()
  }



  sourceSets {

    jsMain {
      //dependsOn(sqliteMain)
      dependencies {

        implementation(project(":core"))
        implementation(libs.compose.runtime)

        implementation(npm("highlight.js", "10.7.2"))
        implementation(libs.compose.html.core)


//        implementation(libs.androidx.sqlite.web)
        //      implementation(libs.kotlinx.browser) // Or latest version
        //implementation(libs.androidx.sqlite.bundled)


      }
    }


  }

}



afterEvaluate {

  kotlin.targets.withType<KotlinNativeTarget>().configureEach {

    binaries {
      executable("krch", buildTypes = setOf(NativeBuildType.DEBUG)) {
        //compilation = compilations["test"]
        entryPoint = "org.danbrough.krch.main"
        if (buildType == NativeBuildType.DEBUG && konanTarget == KonanTarget.LINUX_X64) linkerOpts += "--allow-multiple-definition"

        runTaskProvider?.configure {
          if (project.hasProperty("args")) args(
            project.property("args").toString().split(",").map { it.trim() })
          environment(*System.getenv().keys.filter { it.startsWith("KRCH_") }.map { it to System.getenv(it) }
            .toTypedArray())
        }
      }
    }

  }


  tasks.withType<ShadowJar> {
    mainClass = "org.danbrough.krch.JvmMain"
  }
}


