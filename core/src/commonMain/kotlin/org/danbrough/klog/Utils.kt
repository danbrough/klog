package org.danbrough.klog

import org.danbrough.klog.stdout.Printer
import kotlin.reflect.KClass


interface KLogUtils {
  fun getEnv(name: String): String?
  fun getThreadName(): String

  val stderrPrinter: Printer
  val stdoutPrinter: Printer

  fun <T : Any> loggerName(clazz: KClass<T>): String
}

expect object Utils : KLogUtils {
  override fun getEnv(name: String): String?
  override fun getThreadName(): String
  override val stderrPrinter: Printer
  override val stdoutPrinter: Printer

  override fun <T : Any> loggerName(clazz: KClass<T>): String
}