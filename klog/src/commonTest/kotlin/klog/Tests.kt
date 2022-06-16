package klog

import klog.a.A
import klog.a.a.AA
import klog.a.b.AB
import kotlin.test.Test


class Tests {


  companion object {
    init {
      kLogRegistry.initRegistry(
        Level.TRACE,
        LogFormatters.colored(LogFormatters.verbose),
        LogWriters.stdOut
      )
    }

    //have to provide the fully qualified name for the JS platform
    //otherwise could just use `klog.klog()`
    private val log = klog("klog.Tests")

  }

  private fun runLogTest() {
    A().test()
    AA().test()
    AB().test()
  }

  @Test
  fun testLogConf() {

    kLogRegistry.initRegistry(
      Level.TRACE,
      LogFormatters.colored(LogFormatters.verbose),
      LogWriters.stdOut
    )
    log.info("testings logs ..")
    runLogTest()

    log.trace("setting level on class klog.a.A to DEBUG")
    kLogRegistry["klog.a.A"].level = Level.DEBUG

    log.trace("setting level on package klog.a.a to INFO")
    kLogRegistry["klog.a.a"].level = Level.INFO

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

  }

  @Test
  fun testTime() {
    log.warn("time is: ${getTimeMillis()}")
  }

}