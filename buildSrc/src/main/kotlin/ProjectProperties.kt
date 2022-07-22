import org.gradle.api.JavaVersion
import org.gradle.api.Project
import java.net.URI

object ProjectProperties {
  const val SDK_VERSION = 33
  const val MIN_SDK_VERSION = 23
  const val BUILD_TOOLS_VERSION = "33.0.0"
  val JAVA_VERSION = JavaVersion.VERSION_11

  fun test() {
    System.getProperties().forEach {
      println("PROP: ${it.key} -> ${it.value}")
    }
  }

  val IDE_ACTIVE = System.getProperty("idea.active", "false").toBoolean()

  const val KOTLIN_JVM_VERSION = "11"


/*
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
  }*/


  val Project.LOCAL_MAVEN_REPO: URI
    get() = rootProject.buildDir.resolve("m2").toURI()


  val Project.projectGroup: String
    get() = rootProject.findProperty("project.group")!!.toString().trim()


}


