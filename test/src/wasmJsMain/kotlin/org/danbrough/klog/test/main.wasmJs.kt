package org.danbrough.klog.test

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.js.ExperimentalWasmJsInterop


@OptIn(ExperimentalWasmJsInterop::class)
fun consoleInfo(m: String?): Unit = js("console.info(m)")

@OptIn(ExperimentalWasmJsInterop::class)
actual fun test() {

  println("test()")

  consoleInfo("written via the console")


  val scope = CoroutineScope(Dispatchers.Default)
  scope.launch {
    coroutineTest()
  }


}


fun main(args: Array<String>) = testMain(args)