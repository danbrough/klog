package org.danbrough.klog

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.time.Clock


@Suppress("RedundantNullableReturnType")
@OptIn(ExperimentalWasmJsInterop::class)
fun getEnvWasmJS(name: String): String? = js("process.env[name] || null")

@OptIn(ExperimentalWasmJsInterop::class)
actual fun test() {
  println("doing a wasmjs test at ${Clock.System.now()}")
  //println("HOME is ${getEnvWasmJS("HOME")}")

  var name = "HOME"
  println("$name = ${getEnvWasmJS(name)}")
  name = "HOMEs"
  println("$name = ${getEnvWasmJS(name)}")


  val scope = CoroutineScope(Dispatchers.Default)
  scope.launch {
    coroutineTest()
  }


}
