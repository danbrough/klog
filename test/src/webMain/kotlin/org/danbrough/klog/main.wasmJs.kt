package org.danbrough.klog

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.js.ExperimentalWasmJsInterop


@OptIn(ExperimentalWasmJsInterop::class)
actual fun test() {


  val scope = CoroutineScope(Dispatchers.Default)
  scope.launch {
    coroutineTest()
  }


}


fun main(args: Array<String>) = testMain(args)