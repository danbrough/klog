package org.danbrough.klog.test

import org.danbrough.klog.LoggerBase
import org.danbrough.klog.Named


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



abstract class PropertyResolver<T>(
  protected val nameDelimiter: String = "."
) {


  abstract operator fun get(name: String): T?
  abstract operator fun set(name: String, value: T)

  fun resolve(name: String): T? {
    //println("resolve: $name")
    get(name)?.also {
      //println("got value: $it")
      return it
    }

    val i = name.lastIndexOf(nameDelimiter)
    return if (i > -1) resolve(name.substring(0, i))
    else null
  }
}


open class CachingResolver<T : Named>(
  nameDelimiter: String, val provider: (T?, String) -> T
) : PropertyResolver<T>(nameDelimiter) {
  protected open val cache = mutableMapOf<String, T>()
  override fun get(name: String): T? = cache[name]
  override fun set(name: String, value: T) {
    cache[name] = value
  }

  @Suppress("UNCHECKED_CAST")
  fun resolveOrCreate(name: String): T = get(name) ?: super.resolve(name).let {
    provider(it, name).also { copy ->
      set(copy.name, copy)
    }
  }
}

class Thang(name: String, val parentID: Int = -1) : LoggerBase(name) {


  override fun toString(): String = "$name:$parentID"
}

fun testMain(args: Array<String>) {
  println("running testMain() args: ${args.joinToString(",")}")


  val props = CachingResolver<Thang>("_") { parent, newName ->
    //println("creating for parent: $parent and new name: $newName")
    Thang(newName, parent?.parentID ?: -1)
  }

  props["ROOT"] = Thang("ROOT", 100)
  props["ANOTHER_ROOT"] = Thang("ANOTHER_ROOT", 1000)

  var key = "ROOT"
  println("$key = ${props.resolveOrCreate(key)}")
  key = "ANOTHER_ROOT"
  println("$key = ${props.resolveOrCreate(key)}")
  key = "ROOT_A"
  println("$key = ${props.resolveOrCreate(key)}")
  key = "ROOT_A_B"
  println("$key = ${props.resolveOrCreate(key)}")
  key = "ROOT_A"
  println("$key = ${props.resolveOrCreate(key)}")
  key = "ROOT_C_A"
  println("$key = ${props.resolveOrCreate(key)}")
  key = "ANOTHER_ROOT_A"
  println("$key = ${props.resolveOrCreate(key)}")
  key = "ANOTHER_ROOT_A_B"
  println("$key = ${props.resolveOrCreate(key)}")
  key = "ANOTHER_ROOT_A_B_C"
  println("$key = ${props.resolveOrCreate(key)}")
  key = "ANOTHER_ROOT_A_B"
  println("$key = ${props.resolveOrCreate(key)}")/*  log.trace { "trace()" }
    log.debug { "debug()" }
    log.info { "info()" }
    log.warn { "warn()" }
    log.error { "error()" }
    test()

    log.debug { $$"$HOME is $${Utils.environment["HOME"]}" }
    log.debug { $$"$HOMEZ is $${Utils.environment["HOMEZ"]}" }*/


}