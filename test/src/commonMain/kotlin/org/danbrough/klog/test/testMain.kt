package org.danbrough.klog.test

import org.danbrough.klog.CachingPropertyResolver

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

fun testMain(args: Array<String>) {
  println("running testMain() args: ${args.joinToString(",")}")

  var id = 0

  data class Thang(val name: String, val age: Int = id++)

  val props = CachingPropertyResolver("_") { name ->
    println("looking for prop: $name..")
    Thang(name)
  }

  var key = "ROOT"
  println("key = ${props[key]}")
  key = "ROOT_A"
  println("key = ${props[key]}")


  /*  log.trace { "trace()" }
    log.debug { "debug()" }
    log.info { "info()" }
    log.warn { "warn()" }
    log.error { "error()" }
    test()

    log.debug { $$"$HOME is $${Utils.environment["HOME"]}" }
    log.debug { $$"$HOMEZ is $${Utils.environment["HOMEZ"]}" }*/


}