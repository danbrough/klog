package klog

import klog.Logger.Level


abstract class KLogFactory {
  open var defaultLogLevel = Level.NONE
  abstract fun logger(logName: String): Logger
}

abstract class PropertyResolver<T>(
  protected val nameDelimiter: String = "."
) {
  protected abstract fun getValue(name: String): T?
  protected abstract fun setValue(name: String, value: T)

  open fun resolve(name: String): T? {
    //println("resolve: $name")
    getValue(name)?.also {
      //println("got value: $it")
      return it
    }
    val names = name.split(nameDelimiter).toMutableList()
    val parentName = if (names.size > 1) names.let {
      it.removeLast()
      it.joinToString(nameDelimiter)
    } else null
    //println("$name: parentName: $parentName")
    if (parentName == null) return null
    return resolve(parentName)?.also {
      //println("$name: saving value $it to cache")
      setValue(name, it)
    }
  }
}

open class CachingPropertyResolver<T>(nameDelimiter: String, val getter: (String) -> T?) :
  PropertyResolver<T>(nameDelimiter) {
  private val cache = mutableMapOf<String, T>()

  override fun getValue(name: String): T? = cache[name] ?: getter(name)?.also { cache[name] = it }

  override fun setValue(name: String, value: T) {
    cache[name] = value
  }
}

object EnvPropertyResolver :
  CachingPropertyResolver<Level>("_", { name -> getEnv(name)?.let { Level.valueOf(it) } })

