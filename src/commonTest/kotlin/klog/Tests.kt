package klog

import klog.outputs.outputs
import klog.outputs.stdout
import kotlin.test.Test

class Tests {
  private val rootLog = klog(ROOT_TAG) {
    level = Level.TRACE
  }

  private val testLog = klog {
    level = Level.TRACE
    outputs {
      stdout {

      }
    }
  }


  @Test
  fun test1() {
    testLog.error("test1() error")
    testLog.warn("test1() warn")
    testLog.info("test1() info")
    testLog.debug("test1() debug")
    testLog.trace("test1() trace")

    testLog.info("testLog.context = ${testLog.config}")
    testLog.info("rootLog.context = ${rootLog.config}")
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