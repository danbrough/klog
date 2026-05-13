package org.danbrough.klog

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

actual fun test() {

  println("test()")

  log.info { "info test" }
  logger("THANG").info { "info from THANG logger" }
  val scope = CoroutineScope(Dispatchers.Default)
  scope.launch {
    coroutineTest()
  }


}


fun main(args: Array<String>) = testMain(args)