@file:Suppress("ObjectPropertyName")

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
      val value = project.properties.getOrElse(propName) {
        project.localProperties.getProperty(
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