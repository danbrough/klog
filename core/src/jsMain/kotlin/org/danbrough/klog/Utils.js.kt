package org.danbrough.klog

import org.danbrough.klog.stdout.Printer
import org.danbrough.klog.stdout.StdoutLogging
import kotlin.reflect.KClass


private fun getEnvJS(name: String): String? =
  js("typeof process === 'object' ? process.env[name] : null")

actual object Utils : KLogUtils() {
  actual override val environment: Map<String, String?> =
    object : Map<String, String?> by mapOf("KLOG_LEVEL" to "TRACE") {
      override fun get(key: String): String? = if (key == "KLOG_LEVEL") "TRACE" else getEnvJS(key)
    }

  actual override fun getThreadName(): String = "main"

  actual override val stdoutPrinter: Printer = console::info

  actual override val stderrPrinter: Printer = stdoutPrinter

  actual override fun <T : Any> loggerName(clazz: KClass<T>): String = "KLogger"
}