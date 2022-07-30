package org.danbrough.klog.demo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import klog.*


val log = klog(
  "org.danbrough.klog.demo",
  level = Level.TRACE,
  messageFormatter = KMessageFormatters.verbose.colored,
  writer = KLogWriters.stdOut
).also {
  it.messageFormatter = KMessageFormatters.verbose.colored
}

fun main() {
  println("main()")
  log.trace("trace")
  log.debug("debug")
  log.info("running the main app")
  log.warn {
    "this is the message"
  }
  log.error("an error occurred", Error("Example error"))


  runBlocking(Dispatchers.Default) {
    log.trace("inside coroutine")
  }
  log.trace("finished")
}
