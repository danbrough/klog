@file:Suppress("ObjectPropertyName", "MemberVisibilityCanBePrivate")

import Common_gradle.BuildVersion.buildVersion
import Common_gradle.BuildVersion.buildVersionName
import Common_gradle.Common.getProjectProperty
import Common_gradle.Common.localProperties
import java.io.FileReader
import java.util.*
import kotlin.reflect.KProperty
import kotlin.reflect.jvm.jvmErasure


class ProjectProperty2(val name: String?, val defaultValue: Any?) {

  @Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
  inline operator fun <T : Any?> getValue(thisRef: Any, property: KProperty<*>): T {
    val propName = name ?: property.name
    val propType = property.returnType

    val value = System.getProperties().let {
      if (it.containsKey(propName)) it.getProperty(propName)
      else if (it.containsKey("org.gradle.project.$propName")) it.getProperty("org.gradle.project.$propName")
      else null
    } ?: project.properties.getOrDefault(
      propName, null
    )
    ?: return defaultValue as T

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
    inline operator fun <T : Any?> getValue(project: Project, property: KProperty<*>): T {
      val propName = name ?: property.name
      val propType = property.returnType


      val value = System.getProperties().let {
        if (it.containsKey(propName)) it.getProperty(propName)
        else if (it.containsKey("org.gradle.project.$propName")) it.getProperty("org.gradle.project.$propName")
        else null
      } ?: project.localProperties.getOrElse(propName) {
        project.properties.getOrDefault(
          propName, null
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


  fun createProperty(name: String? = null, defaultValue: Any? = null) =
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
  val Project.buildVersionFormat: String by Common.createProperty(
    "build.snapshot.format",
    defaultValue = "0.0.1-SNAPSHOT"
  )

  val Project.buildVersionName: String
    get() = buildVersionName()


  fun Project.buildVersionName(version: Int = buildVersion) = buildVersionFormat.format(version)


/*
  val buildVersionTask: Task by tasks.creating {
    doLast {
      println(buildVersion)
    }
  }
*/

/*
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
  }*/
}
