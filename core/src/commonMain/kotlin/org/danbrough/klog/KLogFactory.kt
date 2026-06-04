package org.danbrough.klog

import org.danbrough.klog.std.BaseStandardLogFactory


abstract class KLogFactory {
  open var defaultLogLevel = Level.NONE
  abstract fun logger(logName: String): KLogger
}

val klogFactoryNOOP = object : KLogFactory() {
  override fun logger(logName: String) = NOOPLogger
}

val klogFactoryStandard = BaseStandardLogFactory()


/*
abstract class PropertyResolver<T>(
  protected val nameDelimiter: String = "."
) {


  abstract operator fun get(name: String): T?
  abstract operator fun set(name: String, value: T)

  open fun resolve(name: String): T? {
    //println("resolve: $name")
    get(name)?.also {
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
      set(name, it)
    }
  }
}

open class CachingPropertyResolver<T>(nameDelimiter: String, val getter: (String) -> T?) :
  PropertyResolver<T>(nameDelimiter) {
  private val cache = mutableMapOf<String, T>()

  override fun get(name: String): T? = cache[name] ?: getter(name)?.also { cache[name] = it }

  override fun set(name: String, value: T) {
    cache[name] = value
  }
}
*/

/*object EnvPropertyResolver : CachingPropertyResolver<Level>(
  "_", { parentLevel,name -> Utils.environment[name]?.let { Level.valueOf(it) } } ?: Level.NONE)*/

