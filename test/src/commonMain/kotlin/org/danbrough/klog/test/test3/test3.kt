package org.danbrough.klog.test.test3

import kotlinx.coroutines.delay
import org.danbrough.klog.logger
import kotlin.time.Duration.Companion.seconds

suspend fun test3(args: Array<String>) {

  println("test3: ${args.joinToString(",")}")
  val log = logger("ROOT")
  log.trace { "trace message" }
  log.debug { "trace message" }
  log.info { "message from here" }
  log.warn { "warn message" }
  log.error { "error message" }
  

}