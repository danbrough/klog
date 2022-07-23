import org.gradle.api.JavaVersion
import org.gradle.api.Project
import java.io.FileReader
import java.net.URI
import java.util.Properties
import kotlin.reflect.KProperty

object ProjectProperties {
  const val SDK_VERSION = 33
  const val MIN_SDK_VERSION = 23
  const val BUILD_TOOLS_VERSION = "33.0.0"
  val JAVA_VERSION = JavaVersion.VERSION_11
  const val KOTLIN_JVM_VERSION = "11"

  val IDE_ACTIVE = System.getProperty("idea.active", "false").toBoolean()


  lateinit var localProperties: Properties
  var buildVersion: Int = 0
  lateinit var projectGroup: String
  var buildSnapshot = false
  private lateinit var buildVersionFormat: String
  private var buildVersionOffset = 0

  val buildVersionName: String
    get() = buildVersionName()

  private fun buildVersionName(version: Int = buildVersion) =
    buildVersionFormat.format(version - buildVersionOffset)


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

  @Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
  inline fun <reified T : Any?> getProjectProperty(
    project: Project,
    propName: String,
    defaultValue: T
  ): T {
    val value = System.getProperties().let {
      if (it.containsKey(propName)) it.getProperty(propName)
      else if (it.containsKey("org.gradle.project.$propName")) it.getProperty("org.gradle.project.$propName")
      else null
    } ?: localProperties.getOrElse(propName) {
      project.properties.getOrDefault(propName, null)
    } ?: return defaultValue

    value as String
    return when (T::class) {
      String::class -> value
      Int::class -> value.toInt()
      Float::class -> value.toFloat()
      Double::class -> value.toDouble()
      Long::class -> value.toLong()
      Boolean::class -> value.toBoolean()
      else -> throw Error("Invalid property type: ${T::class}")
    } as T
  }


  fun init(project: Project) {
    localProperties = project.rootProject.file("local.properties").let { localPropsFile ->
      Properties().also { props ->
        if (localPropsFile.exists()) FileReader(localPropsFile).use { props.load(it) }
      }
    }

    projectGroup = getProjectProperty(project, "project.group", "org.danbrough")
    buildSnapshot = getProjectProperty(project, "build.snapshot", false)
    buildVersion = getProjectProperty(project, "build.version", 0)
    buildVersionOffset = getProjectProperty(project, "build.version.offset", 0)


    buildVersionFormat = if (buildSnapshot)
      getProjectProperty(project, "build.snapshot.format", "0.0.1-SNAPSHOT")
    else
      getProjectProperty(project, "build.version.format", "0.0.1-alpha$02d")


    project.tasks.create("buildVersion") {
      doLast {
        println(buildVersion)
      }
    }

    project.tasks.create("buildVersionName") {
      doLast {
        println(buildVersionName)
      }
    }

    project.tasks.create("buildVersionNameNext") {
      doLast {
        println(buildVersionName(buildVersion + 1))
      }
    }

    project.tasks.create("buildVersionIncrement") {
      doLast {
        val currentVersion = buildVersion
        project.rootProject.file("gradle.properties").readLines().map {
          if (it.contains("build.version=")) "build.version=${buildVersion + 1}"
          else it
        }.also { lines ->
          project.rootProject.file("gradle.properties").writer().use { writer ->
            lines.forEach {
              writer.write("$it\n")
            }
          }
        }
        println(buildVersionName(buildVersion + 1))
      }
    }


  }

}


