package org.danbrough.klog.test

import org.danbrough.klog.test.test2.test2
import org.danbrough.klog.test.test3.test3


expect fun test()

/*
val log = kloggingStandard {
  formatter = defaultMessageFormatter
  coloredOutput = true
}.let { logger("TEST") }
*/


/*suspend fun coroutineTest() {
  log.debug { "coroutineTEst(): running at ${Clock.System.now()} thread:${Utils.getThreadName()}" }
  val scope = CoroutineScope(Dispatchers.Default)
  scope.launch {
    log.debug { "launched coroutine" }
    delay(2.seconds)
    log.debug { "launched coroutine finished thread:${Utils.getThreadName()}" }
  }

  delay(5.seconds)
}*/


interface PropertyResolver<T> {
  fun parentName(name: String): String? = null
  operator fun get(name: String): T?

  fun parent(name: String): T?

}

interface MutablePropertyResolver<T> : PropertyResolver<T> {
  operator fun set(name: String, value: T)
}


abstract class BasePropertyResolver<T>(val nameDelimiter: String = ".") : PropertyResolver<T> {
  override fun parentName(name: String): String? =
    name.lastIndexOf(nameDelimiter).takeIf { it > 0 }?.let {
      name.substring(0, it)
    }

  private fun parent(name: String, originalName: String): T? =
    get(name) ?: parentName(name)?.let { parentName ->
      parent(parentName, originalName)
    }

  override fun parent(name: String): T? = parent(name, name)
}

fun <T> propertyResolver(
  nameDelimiter: String = ".",
  getter: ((String) -> T?)? = null,
): PropertyResolver<T> = object : BasePropertyResolver<T>(nameDelimiter) {
  override fun get(name: String): T? = getter?.invoke(name)
}

private class CachedPropertyResolver<T>(private val resolver: PropertyResolver<T>) :
  MutablePropertyResolver<T> {
  private val cache = mutableMapOf<String, T>()

  override fun get(name: String): T? = cache[name] ?: resolver[name]?.also {
    cache[name] = it
  }

  override fun parent(name: String): T? = resolver.parent(name)?.also {
    cache[name] = it
  }

  override fun set(name: String, value: T) {
    cache[name] = value
    //if (resolver is MutablePropertyResolver) resolver[name] = value
  }
}

fun <T> PropertyResolver<T>.cached(): MutablePropertyResolver<T> =
  this as? CachedPropertyResolver<T> ?: CachedPropertyResolver(this)

fun <A, B> PropertyResolver<A>.map(mapper: (A) -> B): PropertyResolver<B> =
  object : PropertyResolver<B> {

    override fun get(name: String): B? = this@map[name]?.let(mapper)

    override fun parent(name: String): B? = this@map.parent(name)?.let(mapper)
  }


class Thang(val name: String, val parentID: Int = -1) {
  override fun toString(): String = "$name:$parentID"
}

suspend fun testMain(args: Array<String>) = test3(args)

suspend fun testMain2(args: Array<String>) {
  println("running testMain() args: ${args.joinToString(",")}")

  val props = propertyResolver<String>("_").cached()

  props["ROOT"] = "ROOT"
  props["ANOTHER_ROOT"] = "ANOTHER_ROOT"


  var key = "ROOT"
  println("$key = ${props[key]}")
  key = "ANOTHER_ROOT"
  println("$key = ${props[key]}")
  key = "ROOT_A"
  println("$key = ${props[key]}")
  key = "ROOT_A_B"
  println("$key = ${props[key]}")
  key = "ROOT_A"
  println("$key = ${props[key]}")
  key = "ROOT_C_A"
  println("$key = ${props[key]}")
  key = "ANOTHER_ROOT_A"
  println("$key = ${props[key]}")
  key = "ANOTHER_ROOT_A_B"
  println("$key = ${props[key]}")
  key = "ANOTHER_ROOT_A_B_C"
  println("$key = ${props[key]}")
  key = "ANOTHER_ROOT_A_B"
  println("$key = ${props[key]}")

  val props2 = props.map { Thang(it) }
  println("$key = ${props2[key]}")


  /*  log.trace { "trace()" }
    log.debug { "debug()" }
    log.info { "info()" }
    log.warn { "warn()" }
    log.error { "error()" }
    test()

    log.debug { $$"$HOME is $${Utils.environment["HOME"]}" }
    log.debug { $$"$HOMEZ is $${Utils.environment["HOMEZ"]}" }*/
  println("RUNNING TEST2")
  test2(args)

}