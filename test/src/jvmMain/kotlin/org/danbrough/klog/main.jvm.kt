package org.danbrough.klog

import kotlinx.coroutines.runBlocking

actual fun test() {
  runBlocking {
    coroutineTest()
  }
}