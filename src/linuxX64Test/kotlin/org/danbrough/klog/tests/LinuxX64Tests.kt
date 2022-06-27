package org.danbrough.klog.tests


import org.danbrough.klog.*
import kotlin.test.Test

class LinuxX64Tests {
  private val log =
    klog(
      level = Level.TRACE,
      KLogWriters.stdOut,
      KMessageFormatters.verbose.colored
    )


  @Test
  fun test1() {
    println("test1()!!")
    println("regtistry: $kLogRegistry")
    println("klogname: ${this::class.klogName()}")
    println("LOG: $log")

    log.info("an info message")

    log.trace {
      "A lazy trace message"
    }
  }
}