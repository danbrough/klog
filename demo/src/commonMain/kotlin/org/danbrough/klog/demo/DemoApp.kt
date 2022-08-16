package org.danbrough.klog.demo


import klog.*


private val log by lazy {
  klog("org.danbrough.klog.demo") {
    level = Level.TRACE
    writer = KLogWriters.stdOut
    messageFormatter = KMessageFormatters.verbose.colored
  }
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


}


