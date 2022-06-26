package org.danbrough.klog

import kotlinx.cinterop.memScoped
import platform.posix.fopen
import platform.posix.fwrite
import platform.posix.pthread_self
import kotlin.test.Test

class PosixTests {
/*
  private val log =
    klog(level = Level.TRACE, messageFormatter = KMessageFormatters.verbose.colored, writer = KLogWriters.stdOut)
*/

  @Test
  fun pthreadTest() {
    println("RUNNING PTHREAD TEST")

/*    log.warn("pthreadTest()")

    log.info("PTHREAD: ${pthread_self()}")*/

  }
}