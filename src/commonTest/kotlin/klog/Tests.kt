package klog

import kotlin.test.Test

class Tests {

  private val rootLog = klog(ROOT_TAG) {
    level = Level.TRACE
    colored = false
  }

  private val testLog = klog {
    level = Level.DEBUG
  }


  @Test
  fun test1() {
    testLog.error("test1() error")
    testLog.warn("test1() warn")
    testLog.info("test1() info")
    testLog.debug("test1() debug")
    testLog.trace("test1() trace")

    testLog.info("testLog.context = ${testLog.context}")
    testLog.info("rootLog.context = ${rootLog.context}")
  }

}