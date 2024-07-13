package org.danbrough.klog.support

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.konan.target.HostManager

class SupportPlugin : Plugin<Project> {
  override fun apply(target: Project) {

  }
}


fun KotlinMultiplatformExtension.declareNativeTargets() {
  applyDefaultHierarchyTemplate()

  mingwX64()
  linuxX64()
  linuxArm64()

  androidNativeX64()
  androidNativeArm64()

  if (HostManager.hostIsMac) {
    macosArm64()
    macosX64()
    iosArm64()
    iosX64()
    watchosX64()
    watchosArm64()
    tvosX64()
    tvosArm64()
  }
}