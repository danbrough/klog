@file:Suppress("ObjectPropertyName", "MemberVisibilityCanBePrivate")

import Common_gradle.BuildVersion.buildVersion
import Common_gradle.BuildVersion.buildVersionName
import Common_gradle.Common.getProjectProperty
import java.io.FileReader
import java.util.*
import kotlin.reflect.KProperty


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
  val Project.message: String?
    get() = this.getProjectProperty("message", null)


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

/*
import org.jetbrains.kotlin.konan.target.KonanTarget
import org.jetbrains.kotlin.konan.target.Family
import org.jetbrains.kotlin.konan.target.Architecture
 */
/*

object BuildEnvironment {
  val Project.hostTarget: org.jetbrains.kotlin.konan.target.KonanTarget?
    get() {
      val osName = System.getProperty("os.name")

      val hostArchitecture: org.jetbrains.kotlin.konan.target.Architecture = when (val osArch = System.getProperty("os.arch")) {
        "amd64", "x86_64" -> org.jetbrains.kotlin.konan.target.Architecture.X64
        "arm64", "aarch64" -> org.jetbrains.kotlin.konan.target.Architecture.ARM64
        else -> throw Error("Unknown os.arch value: $osArch")
      }

      return when {
        osName == "Linux" -> {
          when (hostArchitecture) {
            org.jetbrains.kotlin.konan.target.Architecture.ARM64 -> org.jetbrains.kotlin.konan.target.KonanTarget.LINUX_ARM64
            org.jetbrains.kotlin.konan.target.Architecture.X64 -> org.jetbrains.kotlin.konan.target.KonanTarget.LINUX_X64
            else -> null
          }
        }

        osName.startsWith("Mac") -> {
          when (hostArchitecture) {
            org.jetbrains.kotlin.konan.target.Architecture.X64 -> org.jetbrains.kotlin.konan.target.KonanTarget.MACOS_X64
            org.jetbrains.kotlin.konan.target.Architecture.ARM64 -> org.jetbrains.kotlin.konan.target.KonanTarget.MACOS_ARM64
            else -> null
          }
        }

        osName.startsWith("Windows") -> {
          when (hostArchitecture) {
            org.jetbrains.kotlin.konan.target.Architecture.X64 -> org.jetbrains.kotlin.konan.target.KonanTarget.MINGW_X64
            else -> null
          }
        }
        else -> null
      }
    }

}
*/

