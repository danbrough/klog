package org.danbrough.klog.test

import org.danbrough.klog.Env
import org.danbrough.klog.Level
import org.danbrough.klog.Utils
import org.danbrough.klog.defaultToParent
import org.danbrough.klog.logger
import org.danbrough.klog.parentNameResolverDelimited
import org.danbrough.klog.propertyResolver


expect fun test()


suspend fun testMain(args: Array<String>) {
  println("running testMain() args: ${args.joinToString(",")}")

  val log = logger("ROOT")
  log.trace { "trace message klogColored: ${Env.klogColored}" }
  log.debug { "debug message" }
  log.info { "info message level: ${log.level}" }
  log.warn { "warn message" }
  log.error { "error message" }
  log.error(RuntimeException("Runtime exception message")) { "error message with stacktrace" }
  val logLevels =
    propertyResolver { name ->
      Utils.environment[name]?.let {
        Level.valueOf(
          it
        )
      }
    }.defaultToParent(
      parentNameResolverDelimited("_")
    )

  for (key in listOf("A", "A_B", "A_B_C"))
    println("LOG_LEVEL: $key = ${logLevels[key]}")
}

