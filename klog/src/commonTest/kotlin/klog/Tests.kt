package klog

import kotlin.native.concurrent.ThreadLocal
import kotlin.test.Test


class Tests {

  companion object {
    init {
      KLogFactory.rootLogger =
        KLog(Level.TRACE, LogFormatters.colored(LogFormatters.simple), LogWriters.stdOut)
    }


  }

  private val log = klog()

  @Test
  fun test() {
    println("test()")

    log.trace { "trace with lazy message" }
    log.debug("debug message")
    log.info { "INFO MESSAGE" }
    log.warn { "A warning!" }
    val err = Exception("Something bad happened")
    log.error(err.message, err)



  }

}