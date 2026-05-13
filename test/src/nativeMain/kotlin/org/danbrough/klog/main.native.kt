package org.danbrough.klog

import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) = testMain(args)

actual fun test() {
  log.debug { "doing native test thread:${Utils.getThreadName()}" }
  log.info { "doing native test thread:${Utils.getThreadName()}" }
  log.info { "Home is ${Utils.environment["HOME"]}" }

  runBlocking {
    coroutineTest()
  }
}