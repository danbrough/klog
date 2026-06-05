package org.danbrough.klog.test

import org.danbrough.klog.Utils
import org.danbrough.klog.cached
import org.danbrough.klog.default
import org.danbrough.klog.propertyResolver


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



class Thang(val name: String, val parentID: Int = -1) {
  override fun toString(): String = "$name:$parentID"
}

suspend fun testMain(args: Array<String>) = testMain2(args)

suspend fun testMain2(args: Array<String>) {
  println("running testMain() args: ${args.joinToString(",")}")


  val cache = mutableMapOf<String, String>()
  val props = propertyResolver("_", {
    println("env lookup: $it")
    Utils.environment[it]
  }).cached(cache)
    .default { name, parent -> "[$name:$parent]" }


  println("--ROOT: ${props["ROOT"]} ..setting property to root")
  props["--ROOT"] = "root"
  println("cache has ROOT: ${cache["ROOT"]}")


  /*
    val props2 = props.map { Thang(it) }
    println("$key = ${props2[key]}")
  */


  /*  log.trace { "trace()" }
    log.debug { "debug()" }
    log.info { "info()" }
    log.warn { "warn()" }
    log.error { "error()" }
    test()

    log.debug { $$"$HOME is $${Utils.environment["HOME"]}" }
    log.debug { $$"$HOMEZ is $${Utils.environment["HOMEZ"]}" }*/
  println("RUNNING TEST2")


}