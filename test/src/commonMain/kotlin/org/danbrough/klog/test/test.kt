package org.danbrough.klog.test

import org.danbrough.klog.logger


expect fun test()

suspend fun testMain(args: Array<String>) {
  println("running testMain() args: ${args.joinToString(",")}")

  val log = logger("ROOT")
  log.trace { "trace message" }
  log.debug { "debug message" }
  log.info { "info message level: ${log.level}" }
  log.warn { "warn message" }
  log.error { "error message" }
}

