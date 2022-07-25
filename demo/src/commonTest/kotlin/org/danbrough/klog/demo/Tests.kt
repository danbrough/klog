package org.danbrough.klog.demo

import org.danbrough.klog.*
import kotlin.test.Test

class Tests {


  private val log = klog(Level.TRACE, KLogWriters.stdOut, KMessageFormatters.verbose.colored)


  @Test
  fun test1() {

    println("test1()")
    println("klogname: ${this::class.klogName()}")
    println("LOG: $log")

    log.info("an info message")

    log.trace {
      "A lazy trace message"
    }


/*    runBlocking(Dispatchers.Default) {
      log.info("inside coroutine")
    }*/
  }


  @Test
  fun testTime() {
    log.warn("time is: ${getTimeMillis()}")
  }

}