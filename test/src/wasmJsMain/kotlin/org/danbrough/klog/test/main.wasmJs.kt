package org.danbrough.klog.test

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(ExperimentalWasmJsInterop::class)
fun consoleInfo(m: String?): Unit = js("console.info(m)")

@OptIn(ExperimentalWasmJsInterop::class)
actual fun test() {

  println("test()")

  consoleInfo("written via the console")


}


fun main(args: Array<String>) {
  CoroutineScope(Dispatchers.Default).launch { testMain(args) }
}