package org.danbrough.klog.tests

import kotlinx.coroutines.Dispatchers
import platform.posix.printf
import platform.posix.pthread_self
import kotlin.test.Test
import kotlinx.coroutines.runBlocking
import org.danbrough.klog.*

class PosixTests {

  private val log = klog(
    level = Level.TRACE,
    messageFormatter = KMessageFormatters.verbose.colored,
    writer = KLogWriters.stdOut
  )


  @Test
  fun pthreadTest() {
    log.debug("RUNNING PTHREAD TEST")


    val threadID = pthread_self()
    printf("THREAD ID: %x\n", threadID)

    log.info("PTHREAD: ${pthread_self()} ")
    runBlocking(Dispatchers.Default) {
      log.info("inside coroutine: $this")
      log.info("PTHREAD: ${pthread_self()} ")
    }


    log.warn("threadIDD: ${org.danbrough.klog.posix.threadID()}")
    printf("THREAD ID FINALLY: %llu\n",org.danbrough.klog.posix.threadID())
  }
}