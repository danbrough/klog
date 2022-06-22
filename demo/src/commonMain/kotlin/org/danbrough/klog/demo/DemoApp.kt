package org.danbrough.klog.demo

import org.danbrough.klog.*


val log = klog(
  "org.danbrough.klog.demo",
  level = Level.TRACE,
  messageFormatter = KMessageFormatters.verbose.colored,
  writer = KLogWriters.stdOut
)

fun main() {
  log.trace("trace")
  log.debug("debug")
  log.info("running the main app")
  log.warn("")
  log.error("")
}