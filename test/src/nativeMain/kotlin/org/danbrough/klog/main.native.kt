package org.danbrough.klog

import kotlinx.coroutines.runBlocking

actual fun test() {
  log.debug { "doing native test thread:${Utils.getThreadName()}" }
  log.info { "doing native test thread:${Utils.getThreadName()}" }
  log.info { "Home is ${Utils.getEnv("HOME")}" }

  runBlocking {
    coroutineTest()
  }
}