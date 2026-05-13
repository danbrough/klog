package org.danbrough.klog

import kotlin.time.Clock

val log = logger("WEBTEST")
fun main(args: Array<String>) {
  log.trace { "trace message" }
  log.debug { "debug message" }
  log.info { "Hello world! at ${Clock.System.now()} args: ${args.size}:[${args.joinToString(",")}]" }
  log.warn { "warn message" }
  log.error { "error message" }
}