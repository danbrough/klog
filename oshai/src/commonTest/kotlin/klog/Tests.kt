package klog

import kotlin.test.Test


class Tests {

  companion object {
    init {
      kloggingOshai {
        
      }
    }

    private val log by logger()
  }

  @Test
  fun test1() {
    log.trace { "test1() trace" }
    log.debug { "test1() debug" }
    log.info { "test1() info" }
    log.warn { "test1() warn" }
    log.error(Exception("Test Exception")) { "test1() error" }
  }
}