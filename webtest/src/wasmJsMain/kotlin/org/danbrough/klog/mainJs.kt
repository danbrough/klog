package org.danbrough.klog

import kotlin.time.Clock

val log = logger("WEBTEST")
fun main(args: Array<String>) {
  log.info { "Hello world! at ${Clock.System.now()} args: ${args.size}:[${args.joinToString(",")}]" }
}