@file:Suppress("ObjectPropertyName", "MemberVisibilityCanBePrivate")

import Common_gradle.BuildVersion.buildVersion
import Common_gradle.BuildVersion.buildVersionName
import Common_gradle.Common.getProjectProperty
import java.io.FileReader
import java.util.*
import kotlin.reflect.KProperty
import org.jetbrains.kotlin.konan.target.KonanTarget
import org.jetbrains.kotlin.konan.target.Architecture

tasks.create("buildVersion") {
  doLast {
    println(project.buildVersion)
  }
}

tasks.create("buildVersionName") {
  doLast {
    println(project.buildVersionName)
  }
}

tasks.create("buildVersionNext") {
  doLast {
    println("NEXT: " + project.buildVersionName(project.buildVersion + 1))
  }
}

tasks.create("buildVersionIncrement") {
  doLast {
    val currentVersion = project.buildVersion
    rootProject.file("gradle.properties").readLines().map {
      if (it.contains("build.version=")) "build.version=${currentVersion + 1}"
      else it
    }.also { lines ->
      rootProject.file("gradle.properties").writer().use { writer ->
        lines.forEach {
          writer.write("$it\n")
        }
      }
    }
    println(project.buildVersionName(project.buildVersion + 1))
  }
}



object Common {

  @Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
  inline fun <reified T : Any?> Project.getProjectProperty(propName: String, defaultValue: T): T {
    val value = System.getProperties().let {
      if (it.containsKey(propName)) it.getProperty(propName)
      else if (it.containsKey("org.gradle.project.$propName")) it.getProperty("org.gradle.project.$propName")
      else null
    } ?: project.localProperties.getOrElse(propName) {
      project.properties.getOrDefault(
        propName, null
      )
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


  class ProjectProperty(val name: String?, val defaultValue: Any?) {

    @Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
    inline operator fun <reified T : Any?> getValue(project: Project, property: KProperty<*>): T =
      project.getProjectProperty(name ?: property.name, defaultValue as T)

  }

  fun <T : Any?> createProperty(name: String? = null, defaultValue: T) =
    ProjectProperty(name, defaultValue)


  private var _localProperties: Properties? = null

  val Project.localProperties: Properties
    get() = _localProperties ?: rootProject.file("local.properties").let { localPropsFile ->
      Properties().also { props ->
        if (localPropsFile.exists()) FileReader(localPropsFile).use { props.load(it) }
        _localProperties = props
      }
    }
}

object BuildVersion {

  val Project.buildVersion: Int by Common.createProperty("build.version", defaultValue = 0)
  val Project.buildVersionOffset: Int by Common.createProperty(defaultValue = 0)
  val Project.buildIsSnapshot: Boolean by Common.createProperty("build.snapshot", true)

  private val Project.versionFormatRegular: String by Common.createProperty(
    "build.version.format",
    defaultValue = "0.0.1-alpha%02d"
  )

  private val Project.versionFormatSnapshot: String by Common.createProperty(
    "build.snapshot.format",
    defaultValue = "0.0.1-SNAPSHOT"
  )

  val Project.buildVersionFormat: String
    get() = if (buildIsSnapshot) versionFormatSnapshot else versionFormatRegular


  val Project.buildVersionName: String
    get() = buildVersionName()


  fun Project.buildVersionName(version: Int = buildVersion) = buildVersionFormat.format(version)


}


object BuildEnvironment {

  val Project.hostTarget: KonanTarget
    get() {
      val osName = System.getProperty("os.name")
      val osArch = System.getProperty("os.arch")
      val hostArchitecture: Architecture =
        when ( osArch ) {
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