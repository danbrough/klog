package org.danbrough.klog

import klog.Utils
import kotlinx.coroutines.runBlocking
import platform.posix.pthread_self

actual fun test() {
  log.debug { "doing native test thread:${Utils.getThreadName()}" }
  log.debug { "doing native test thread:${Utils.getThreadName()}" }

  runBlocking {
    coroutineTest()
  }
}