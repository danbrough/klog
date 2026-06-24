package org.danbrough.klog

import org.danbrough.klog.std.StandardLogFactory
import org.danbrough.klog.std.Printer
import kotlin.reflect.KClass

private val klogFactoryStandard = StandardLogFactory()

interface KLogUtils {

  val environment: Map<String, String?>
  fun getThreadName(): String

  val stderrPrinter: Printer
  val stdoutPrinter: Printer

  fun <T : Any> loggerName(clazz: KClass<T>): String

  fun defaultLogFactory(): KLogFactory = klogFactoryStandard

}

expect object Utils : KLogUtils {
  override val environment: Map<String, String?>

  override fun getThreadName(): String
  override val stderrPrinter: Printer
  override val stdoutPrinter: Printer
  override fun <T : Any> loggerName(clazz: KClass<T>): String
}