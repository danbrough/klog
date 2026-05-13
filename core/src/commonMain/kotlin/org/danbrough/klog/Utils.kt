package org.danbrough.klog

import org.danbrough.klog.stdout.Printer
import org.danbrough.klog.stdout.StdoutLogging
import kotlin.reflect.KClass


abstract class KLogUtils {

  abstract val environment: Map<String, String?>
  abstract fun getThreadName(): String

  abstract val stderrPrinter: Printer
  abstract val stdoutPrinter: Printer

  abstract fun <T : Any> loggerName(clazz: KClass<T>): String

  open fun defaultLogFactory(): KLogFactory = StdoutLogging()
}

expect object Utils : KLogUtils {
  override val environment: Map<String, String?>
  override fun getThreadName(): String
  override val stderrPrinter: Printer
  override val stdoutPrinter: Printer
  override fun <T : Any> loggerName(clazz: KClass<T>): String

  override fun defaultLogFactory(): KLogFactory
}