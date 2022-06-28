package org.danbrough.klog.tests


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.danbrough.klog.*
import platform.posix.pthread_self
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

    log.info("PTHREAD: ${pthread_self()} ")
    runBlocking(Dispatchers.Default) {
      log.info("inside coroutine: $this")
      log.info("PTHREAD: ${pthread_self()} ")
    }

    log.warn("threadID: ${org.danbrough.klog.posix.threadID()}")
  }
}