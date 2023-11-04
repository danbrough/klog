package klog

import kotlin.test.Test

class Tests {

  private val rootLog = klog(ROOT_TAG) {
    colored = false
  }

  private val testLog = klog {
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

  @Test
  fun testGetEnv(){
    testLog.info("testGetEnv()")
    testLog.debug("HOME=${getenv("HOME")}")
  }
}