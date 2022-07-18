package org.danbrough.klog.tests

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.danbrough.klog.*
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class AndroidTests {

  private val log = klog(Level.TRACE, KLogWriters.stdOut, KMessageFormatters.verbose.colored)

  @Test
  fun test() {
    log.trace("trace")
    log.debug("debug")
    log.info("info")
    log.warn("warn")
    log.error("error")
  }
}