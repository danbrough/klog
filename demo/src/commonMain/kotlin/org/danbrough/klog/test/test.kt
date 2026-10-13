package org.danbrough.klog.test

import org.danbrough.klog.Env
import org.danbrough.klog.Level
import org.danbrough.klog.ParentNameResolver
import org.danbrough.klog.PropertyResolver
import org.danbrough.klog.Utils
import org.danbrough.klog.logger
import org.danbrough.klog.propertyResolver


expect fun test()

fun defaultParentNameResolver2(nameDelimiter: String): ParentNameResolver = { name ->
  name.lastIndexOf(nameDelimiter).takeIf { it > 0 }?.let {
    name.substring(0, it)
  }
}

fun <T> propertyResolver2(
  nameDelimiter: String = ".", getter: (String) -> T?
): PropertyResolver<T> = object : PropertyResolver<T> {
  override fun get(name: String): T? = getter(name)

  override fun set(name: String, value: T) {}
  override var parentName: ParentNameResolver = defaultParentNameResolver2(nameDelimiter)
  private fun parent(name: String, originalName: String): T? {
    return (get(name) ?: parentName(name)?.let { parentName ->
      parent(parentName, originalName)
    })
  }

  override fun parent(name: String): T? = parent(name, name)
}

suspend fun testMain(args: Array<String>) {
  println("running testMain() args: ${args.joinToString(",")}")

  val log = logger("ROOT")
  log.trace { "trace message klogColored: ${Env.klogColored}" }
  log.debug { "debug message" }
  log.info { "info message level: ${log.level}" }
  log.warn { "warn message" }
  log.error { "error message" }
  log.error(RuntimeException("Runtime exception message")) { "error message with stacktrace" }
  val logLevels =
    propertyResolver("_") { name -> Utils.environment[name]?.let { Level.valueOf(it) } }

  for (key in listOf("A", "A_B", "A_B_C"))
    println("LOG_LEVEL: $key = ${logLevels[key]}")
}

