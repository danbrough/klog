import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.creating
import java.net.URI
import java.io.File

object ProjectProperties {
  const val SDK_VERSION = 33
  const val MIN_SDK_VERSION = 23
  const val BUILD_TOOLS_VERSION = "33.0.0"
  val JAVA_VERSION = JavaVersion.VERSION_11

  val IDE_ACTIVE = System.getProperty("idea.active", "false").toBoolean()

  const val KOTLIN_JVM_VERSION = "11"

  enum class Platform(val os: OS, val arch: Arch) {
    MacOS_X64(OS.MacOS, Arch.AMD_64),
    MacOS_Arm64(OS.MacOS, Arch.ARM_64),
    Windows_X64(OS.Windows, Arch.AMD_64),
    Linux_X64(OS.Linux, Arch.AMD_64),
    Linux_Arm64(OS.Linux, Arch.ARM_64);

    enum class OS {
      Linux, Windows, MacOS
    }

    enum class Arch {
      AMD_64, ARM_64
    }
  }

  fun getHostPlatform(): Platform {
    val osName = System.getProperty("os.name").toString()
    val osArch = System.getProperty("os.arch").toString()
    return when {
      osName.startsWith("Windows") ->
        when {
          osArch == "X86_64" -> Platform.Windows_X64
          else -> TODO("Add support for Windows $osArch")
        }
      osName.startsWith("Linux") ->
        when {
          osArch == "X86_64" -> Platform.Linux_X64
          osArch == "aarch64" -> Platform.Linux_Arm64
          else -> TODO("Add support for Linux $osArch")
        }
      osName.startsWith("Mac") ->
        when {
          osArch == "X86_64" -> Platform.MacOS_X64
          osArch == "aarch64" -> Platform.MacOS_Arm64
          else -> TODO("Add support for Mac $osArch")
        }
      else -> TODO("Add support for $osName:$osArch")
    }
  }


  val Project.LOCAL_MAVEN_REPO: URI
    get() = project.buildDir.resolve("maven").toURI()


  val Project.projectGroup: String
    get() = rootProject.findProperty("project.group")!!.toString().trim()


}


