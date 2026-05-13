package org.danbrough.klog

import org.danbrough.klog.KLogUtils
import org.danbrough.klog.stdout.Printer
import org.danbrough.klog.stdout.StdoutLogging
import kotlin.js.ExperimentalWasmJsInterop
import kotlin.js.js
import kotlin.reflect.KClass


@Suppress("RedundantNullableReturnType")
@OptIn(ExperimentalWasmJsInterop::class)
private fun getEnvWasmJS(name: String): String? = js("process.env[name] || null")


actual object Utils : KLogUtils() {
  @OptIn(ExperimentalWasmJsInterop::class)
  actual override val environment: Map<String, String?> = object : Map<String, String?> by emptyMap() {
    override fun get(key: String): String? = getEnvWasmJS(key)
  }

  actual override fun getThreadName(): String = ""

  actual override val stderrPrinter: Printer = ::println
  actual override val stdoutPrinter: Printer = ::println
  actual override fun <T : Any> loggerName(clazz: KClass<T>): String = "KLogger"

}