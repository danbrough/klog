package org.danbrough.klog.test

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.danbrough.klog.logger

actual fun test() {

  println("test()")

  //log.info { "info test" }
  logger("THANG").info { "info from THANG logger" }
  val scope = CoroutineScope(Dispatchers.Default)
  /*
    scope.launch {
      coroutineTest()
    }
  */


}


fun main(args: Array<String>) = testMain(args)