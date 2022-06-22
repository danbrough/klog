package org.danbrough.klog

import platform.posix.pthread_self
import kotlin.test.Test

class PosixTests {
  private val log =
    klog(level = Level.TRACE, messageFormatter = KMessageFormatters.verbose.colored, writer = KLogWriters.stdOut)

  @Test
  fun pthreadTest() {
    log.warn("pthreadTest()")

    log.info("PTHREAD: ${pthread_self()}")

  }
}