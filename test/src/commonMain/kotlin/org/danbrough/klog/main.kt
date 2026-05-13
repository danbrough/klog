package org.danbrough.klog

import org.danbrough.klog.stdout.defaultMessageFormatter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Clock
import kotlin.time.Duration.Companion.seconds

val log = kloggingStdout {
  formatter = defaultMessageFormatter
  coloredOutput = true
}.let { logger("TEST") }

expect fun test()


suspend fun coroutineTest() {
  log.debug { "coroutineTEst(): running at ${Clock.System.now()} thread:${Utils.getThreadName()}" }
  val scope = CoroutineScope(Dispatchers.Default)
  scope.launch {
    log.debug { "launched coroutine" }
    delay(2.seconds)
    log.debug { "launched coroutine finished thread:${Utils.getThreadName()}" }
  }

  delay(5.seconds)
}

fun testMain(args: Array<String>) {
  println("running testMain() args: ${args.joinToString(",")}")
  log.trace { "trace()" }
  log.debug { "debug()" }
  log.info { "info()" }
  log.warn { "warn()" }
  log.error { "error()" }
  test()

  log.debug { $$"$HOME is $${Utils.getEnv("HOME")}" }
  log.debug { $$"$HOMEZ is $${Utils.getEnv("HOMEZ")}" }


}