import org.jetbrains.kotlin.konan.target.Architecture
import org.jetbrains.kotlin.konan.target.KonanTarget

object BuildEnvironment {
  val KonanTarget.platformName: String
    get() = name.split('_').joinToString("") { it.capitalize() }.decapitalize()

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