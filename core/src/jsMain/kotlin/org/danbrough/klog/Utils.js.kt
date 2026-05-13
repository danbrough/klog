package org.danbrough.klog

import org.danbrough.klog.stdout.Printer
import org.danbrough.klog.stdout.StdoutLogging
import kotlin.reflect.KClass

actual object Utils : KLogUtils() {
  actual override val environment: Map<String, String?> = mapOf("KLOG_LEVEL" to "TRACE")

  actual override fun getThreadName(): String = "main"

  actual override val stdoutPrinter: Printer = console::info

  actual override val stderrPrinter: Printer = stdoutPrinter

  actual override fun <T : Any> loggerName(clazz: KClass<T>): String = "KLogger"
}