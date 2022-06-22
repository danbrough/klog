package org.danbrough.klog.demo

import org.danbrough.klog.*


val log = klog(
  "org.danbrough.klog.demo",
  level = Level.TRACE,
  formatter = KLogFormatters.verbose.colored,
  writer = KLogWriters.stdOut
)

fun main() {
  log.info("running the main app")
}