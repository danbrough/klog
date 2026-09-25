package org.danbrough.klog.test

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.danbrough.klog.logger
import kotlin.time.Duration.Companion.seconds

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


fun main(args: Array<String>) {
  CoroutineScope(Dispatchers.Default).launch {
    testMain(args)
    println("finished testMain")
    delay(2.seconds)
    println("finished delay")
  }.invokeOnCompletion {
    println("done")
  }
}