package org.danbrough.klog.test

import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) = runBlocking { testMain(args) }

actual fun test() {
  /*  log.debug { "doing native test thread:${Utils.getThreadName()}" }
    log.info { "doing native test thread:${Utils.getThreadName()}" }
    log.info { "Home is ${Utils.environment["HOME"]}" }

    runBlocking {
      coroutineTest()
    }*/
}