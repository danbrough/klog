package org.danbrough.klog.test

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

actual fun test() {
}


fun main(args: Array<String>) {
  println("args: ${args.contentToString()}")
  CoroutineScope(Dispatchers.Default).launch {
    testMain(args)
    println("finished testMain")
    delay(2.seconds)
    println("finished delay")
  }.invokeOnCompletion {
    println("done")
  }
}