package klog

import klog.a.A
import klog.a.a.AA
import klog.a.b.AB
import kotlin.test.Test


class Tests {

  companion object {
    init {
      logFactory.rootLog =
        KLog("", Level.TRACE, LogFormatters.colored(LogFormatters.verbose), LogWriters.stdOut)

    }

    private val log = klog()

  }

  private fun runLogTest() {
    A().test()
    AA().test()
    AB().test()
  }

  @Test
  fun testLogs() {
    logFactory.reset()
    log.info("testings logs ..")
    runLogTest()
    log.trace("setting level on class klog.A to DEBUG")
    logFactory["klog.a"].level = Level.DEBUG

    log.trace("setting level on package klog.a.a to INFO")
    logFactory["klog.a.a"].level = Level.INFO
    runLogTest()

  }

  @Test
  fun test() {
    println("test()")

    log.trace { "trace with lazy message" }
    log.debug("debug message")
    log.info { "INFO MESSAGE" }
    log.warn {
      "A warning!"
    }
    val err = Exception("Something bad happened")
    log.error(err.message, err)

    testLogs()
  }

}