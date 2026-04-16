package org.danbrough.klog

import klog.Utils
import klog.kloggingStdout
import klog.stdout.StdoutLogging
import klog.stdout.defaultMessageFormatter

val log = kloggingStdout {
  formatter = defaultMessageFormatter
  coloredOutput = true
}.let { klog.logger("TEST") }

expect fun test()

fun main(args: Array<String>) {
  println("running main() args: ${args.joinToString(",")}")
  log.trace { "trace()" }
  log.debug { "debug()" }
  log.info { "info()" }
  log.warn { "warn()" }
  log.error { "error()" }
  test()

  log.debug { $$"$HOME is $${Utils.getEnv("HOME")}" }
}