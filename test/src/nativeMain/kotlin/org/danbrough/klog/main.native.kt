package org.danbrough.klog

import klog.Utils
import kotlinx.coroutines.runBlocking
import platform.posix.pthread_self

actual fun test() {
  log.debug { "doing native test thread:${Utils.getThreadName()}" }
  log.info { "doing native test thread:${Utils.getThreadName()}" }
  log.info { "Home is ${Utils.getEnv("HOME")}" }

  runBlocking {
    coroutineTest()
  }
}