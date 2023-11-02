package klog

import kotlin.test.Test

class Tests {

  private val log = klog()

  @Test
  fun test1(){
    log.error("test1() error")
    log.warn("test1() warn")
    log.info("test1() info")
    log.debug("test1() debug")
    log.trace("test1() trace")
  }

}