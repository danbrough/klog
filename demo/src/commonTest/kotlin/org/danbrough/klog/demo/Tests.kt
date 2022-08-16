package org.danbrough.klog.demo

import klog.*
import kotlin.test.Test
import    kotlinx.coroutines.runBlocking

class Tests {

  private val log = klog {
    level = Level.TRACE
    writer = KLogWriters.stdOut
    messageFormatter = KMessageFormatters.verbose.colored
  }

  @Test
  fun test1() {

    println("test1()")
    println("klogname: ${this::class.klogName()}")
    println("LOG: $log")

    log.info("an info message")

    log.trace {
      "A lazy trace message"
    }


    runBlocking {
      log.info("message inside coroutine")
    }
  }


  @Test
  fun testTime() {
    log.warn("time is: ${getTimeMillis()}")
  }

}
