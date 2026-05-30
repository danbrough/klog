@file:OptIn(ExperimentalWasmJsInterop::class)

package org.danbrough.klog

import org.danbrough.klog.std.Printer
import kotlin.js.ExperimentalWasmJsInterop
import kotlin.js.js
import kotlin.reflect.KClass


@Suppress("RedundantNullableReturnType")
private fun getEnvJS(name: String): String? =
  js("typeof process === 'object' ? process.env[name] : null")

private fun console(s: String?): Unit = js("console.info(s)")

actual object Utils : KLogUtils() {
  @OptIn(ExperimentalWasmJsInterop::class)
  actual override val environment: Map<String, String?> =
    object : Map<String, String?> by emptyMap() {
      override fun get(key: String): String? = if (key == "KLOG_LEVEL") "TRACE" else getEnvJS(key)
    }

  actual override fun getThreadName(): String = ""

  actual override val stderrPrinter: Printer = { console("$it") }
  actual override val stdoutPrinter: Printer = stderrPrinter
  actual override fun <T : Any> loggerName(clazz: KClass<T>): String = "KLogger"

}