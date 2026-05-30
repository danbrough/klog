package org.danbrough.klog.test

import kotlinx.coroutines.runBlocking
import org.danbrough.klog.Utils

fun main(args: Array<String>) = testMain(args)

actual fun test() {
  log.debug { "doing native test thread:${Utils.getThreadName()}" }
  log.info { "doing native test thread:${Utils.getThreadName()}" }
  log.info { "Home is ${Utils.environment["HOME"]}" }

  runBlocking {
    coroutineTest()
  }
}