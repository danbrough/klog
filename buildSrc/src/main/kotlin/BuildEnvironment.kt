@file:Suppress("MemberVisibilityCanBePrivate")

import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinTargetPreset
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.konan.target.Architecture
import org.jetbrains.kotlin.konan.target.Family
import org.jetbrains.kotlin.konan.target.KonanTarget

object BuildEnvironment {
  val KonanTarget.platformName: String
    get() = platformNameCapitalized.decapitalize()

  val KonanTarget.platformNameCapitalized: String
    get() = name.split('_').joinToString("") { it.capitalize() }

  val KonanTarget.presetName: String
    get() = if (family == Family.ANDROID)
      platformName.replace("android", "androidNative")
    else platformName

  val supportedTargets: Set<KonanTarget> =
    setOf(
      KonanTarget.LINUX_X64,
      KonanTarget.LINUX_ARM64,
      KonanTarget.LINUX_ARM32_HFP,
      KonanTarget.MINGW_X64,
      KonanTarget.MACOS_ARM64,
      KonanTarget.MACOS_X64,
      KonanTarget.IOS_ARM64,
      KonanTarget.IOS_X64,
      KonanTarget.IOS_SIMULATOR_ARM64,
      KonanTarget.TVOS_ARM64,
      KonanTarget.TVOS_X64,
      KonanTarget.TVOS_SIMULATOR_ARM64,
      KonanTarget.WATCHOS_ARM64,
      KonanTarget.WATCHOS_X64,
      KonanTarget.WATCHOS_X86,
      KonanTarget.WATCHOS_ARM32,
      KonanTarget.WATCHOS_SIMULATOR_ARM64,
      KonanTarget.ANDROID_ARM32,
      KonanTarget.ANDROID_ARM64,
      KonanTarget.ANDROID_X86,
      KonanTarget.ANDROID_X64,
    )

  fun KotlinMultiplatformExtension.registerTarget(
    konanTarget: KonanTarget, conf: KotlinNativeTarget.() -> Unit = {}
  ): KotlinNativeTarget {
    @Suppress("UNCHECKED_CAST")
    val preset: KotlinTargetPreset<KotlinNativeTarget> =
      presets.getByName(konanTarget.platformName) as KotlinTargetPreset<KotlinNativeTarget>
    return targetFromPreset(preset, konanTarget.platformName, conf)
  }

  fun KotlinMultiplatformExtension.createNativeTargets() {
    supportedTargets.forEach { konanTarget ->
      targetFromPreset(presets.getByName(konanTarget.presetName), konanTarget.presetName)
    }
  }


  val hostTarget: KonanTarget
    get() {
      val osName = System.getProperty("os.name")
      val osArch = System.getProperty("os.arch")
      val hostArchitecture: Architecture = when (osArch) {
        "amd64", "x86_64" -> Architecture.X64
        "arm64", "aarch64" -> Architecture.ARM64
        else -> throw Error("Unknown os.arch value: $osArch")
      }

      return when {
        osName == "Linux" -> {
          when (hostArchitecture) {
            Architecture.ARM64 -> KonanTarget.LINUX_ARM64
            Architecture.X64 -> KonanTarget.LINUX_X64
            else -> null
          }
        }

        osName.startsWith("Mac") -> {
          when (hostArchitecture) {
            Architecture.X64 -> KonanTarget.MACOS_X64
            Architecture.ARM64 -> KonanTarget.MACOS_ARM64
            else -> null
          }
        }

        osName.startsWith("Windows") -> {
          when (hostArchitecture) {
            Architecture.X64 -> KonanTarget.MINGW_X64
            else -> null
          }
        }
        else -> null
      } ?: throw Error("Unknown build host: $osName:$osArch")
    }
}