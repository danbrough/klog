@file:Suppress("ObjectPropertyName", "MemberVisibilityCanBePrivate")

import org.gradle.api.Project
import java.io.FileReader
import java.util.Properties
import kotlin.reflect.KProperty
import kotlin.reflect.jvm.jvmErasure


object Common {

  class ProjectProperty(val name: String?, val defaultValue: Any?) {

    @Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
    inline operator fun <T : Any?> getValue(project: Project, property: KProperty<*>): T {
      val propName = name ?: property.name
      val propType = property.returnType


      val value =
        System.getProperties().let {
          if (it.containsKey(propName))
            it.getProperty(propName)
          else if (it.containsKey("org.gradle.project.$propName"))
            it.getProperty("org.gradle.project.$propName")
          else null
        } ?: project.localProperties.getOrElse(propName) {
          project.properties.getOrDefault(
            propName,
            null
          )
        } ?: return defaultValue as T

      value as String
      return when (propType.jvmErasure) {
        String::class -> value
        Int::class -> value.toInt()
        Float::class -> value.toFloat()
        Double::class -> value.toDouble()
        Long::class -> value.toLong()
        Boolean::class -> value.toBoolean()
        else -> throw Error("Invalid property type: $propType")
      } as T
    }
  }


  private fun createProperty(name: String? = null, defaultValue: Any? = null) =
    ProjectProperty(name, defaultValue)

  val Project.message: String by createProperty()


  private var _localProperties: Properties? = null

  val Project.localProperties: Properties
    get() = _localProperties ?: rootProject.file("local.properties").let { localPropsFile ->
      Properties().also { props ->
        if (localPropsFile.exists()) props.load(FileReader(localPropsFile))
        _localProperties = props
      }
    }


}


object BuildVersion {

  var buildVersion = 0
  var buildVersionOffset: Int = 0
  var buildVersionFormat: String = "0.0.1-alpha%02d"


  val buildVersionName: String
    get() = buildVersionFormat.format(buildVersion)

  fun init(project: Project) {

    val getProjectProperty: (String, String) -> String = { key, defValue ->
      project.rootProject.findProperty(key)?.toString()?.trim() ?: run {
        project.rootProject.file("gradle.properties").appendText("\n$key=$defValue\n")
        defValue
      }
    }

    val snapshotVersion = getProjectProperty("build.snapshot", "true").toBoolean()
    val snapshotFormat = getProjectProperty("build.snapshot.format", "0.0.1-SNAPSHOT")

    buildVersion = getProjectProperty("build.version", "0").toInt()
    buildVersionFormat =
      if (snapshotVersion) snapshotFormat else
        getProjectProperty("build.version.format", "0.0.1-alpha%02d")
    buildVersionOffset = getProjectProperty("build.version.offset", "0").toInt()


    project.task("buildVersionIncrement") {
      doLast {
        val currentVersion = buildVersion
        project.rootProject.file("gradle.properties").readLines().map {
          if (it.contains("build.version=")) "build.version=${currentVersion + 1}"
          else it
        }.also { lines ->
          project.rootProject.file("gradle.properties").writer().use { writer ->
            lines.forEach {
              writer.write("$it\n")
            }
          }
        }
      }
    }

    project.task("buildVersion") {
      doLast {
        println(buildVersion)
      }
    }

    project.task("buildVersionName") {
      doLast {
        println(buildVersionName)
      }
    }


    project.task("nextBuildVersionName") {
      doLast {
        println(buildVersionFormat.format(buildVersion + 1))
      }
    }
  }
}
