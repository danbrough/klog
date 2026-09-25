package org.danbrough.klog.test

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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


@OptIn(DelicateCoroutinesApi::class)
fun main(args: Array<String>) {
  GlobalScope.launch {
    testMain(args)
  }
}