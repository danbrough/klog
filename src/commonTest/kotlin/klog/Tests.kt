package klog

import klog.outputs.outputs
import klog.outputs.stdout
import kotlin.test.Test

private val rootLog = klog(ROOT_PATH) {
  level = Level.TRACE
  name = "KLogTests"
  outputs {
    stdout {
    }
  }
}

private val testLog = klog("klog.Tests") {
  level = Level.DEBUG
  name = "TestLog"
}

class Tests {

  @Test
  fun test1() {
    rootLog.info("this is from the root log")
    testLog.error("test1() error")
    testLog.warn("test1() warn")
    testLog.info("test1() info")
    testLog.debug("test1() debug")
    testLog.trace("test1() trace")

    testLog.info("testLog.context = ${testLog.logger}")
    testLog.info("rootLog.context = ${rootLog.logger}")
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


}