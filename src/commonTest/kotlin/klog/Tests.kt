package klog

import klog.formatting.colored
import klog.formatting.simpleFormatter
import klog.outputs.outputs
import klog.outputs.stdout
import kotlin.test.Test

private val rootLog = klog {
  level = Level.TRACE
  name = "KLogTests"

  outputs {
    stdout {
      formatter = simpleFormatter.colored
    }
  }
}

class Tests {

  companion object {


    private val testLog = klog("klog.Tests") {
      level = Level.DEBUG
      name = "TestLog"
    }

    private val testLog2 = klog("TESTLOG2").also {
      println("TESTLOG2: $it")
    }

  }


  @Test
  fun test1() {

    rootLog as KLogger
    rootLog.warn("test1(): rootLog.name=${rootLog.name} rootLog.path=${rootLog.path}")

    rootLog.trace("trace: this is from the root log")
    rootLog.debug("debug: this is from the root log")
    rootLog.info("info: this is from the root log")
    rootLog.warn("warn: this is from the root log")
    rootLog.error("error: this is from the root log")

    println("TEST LOG: $testLog")

    testLog.error("test1() error")
    testLog.warn("test1() warn")
    testLog.info("test1() info")
    testLog.debug("test1() debug")
    testLog.trace("test1() trace")

    testLog.info("testLog.context = $testLog")
    testLog.info("rootLog.context = $rootLog")
  }

  @Test
  fun testGetEnv() {

    testLog.info("testGetEnv()")
    testLog.debug("HOME=${getenv("HOME")}")
  }


  @Test
  fun test2() {
    testLog.info("testRegistry()")
    var ctx = klog("dude") {
      level = Level.DEBUG
    }
    testLog.trace("dude: $ctx")
    ctx = klog("dude.1") {
      level = Level.INFO
    }
    testLog.trace("dude.1: $ctx")
  }

  @Test
  fun test3() {
    testLog.info("test3()")
    val err = Exception("Test Error")

    testLog.error(err.message!!, err)
  }

  @Test
  fun test4() {
    testLog.info("test4()")

    testLog.debug("stacktrace: ${getStackTrace()}")
  }


}