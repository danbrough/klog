package org.danbrough.klog

import kotlin.time.Clock

val log = logger("WEBTEST")
fun main(args: Array<String>) {
  log.info { "Hello world! at ${Clock.System.now()} args: ${args.size}:[${args.joinToString(",")}]" }
  log.trace { "TRACE: Hello world! at ${Clock.System.now()} " }
  log.debug { "TRACE: Hello world! at ${Clock.System.now()} " }
  log.info { "TRACE: Hello world! at ${Clock.System.now()} " }
  log.warn { "TRACE: Hello world! at ${Clock.System.now()} " }
  log.error { "TRACE: Hello world! at ${Clock.System.now()} " }
}