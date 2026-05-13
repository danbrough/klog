package org.danbrough.klog

import org.danbrough.klog.stdout.Printer
import kotlin.reflect.KClass

actual object Utils : KLogUtils {
  actual override fun getEnv(name: String): String? = null

  actual override fun getThreadName(): String = "main"

  actual override val stdoutPrinter: Printer = {
    println("WRITING $it")
    console.info(it)
  }

  actual override val stderrPrinter: Printer = stdoutPrinter

  actual override fun <T : Any> loggerName(clazz: KClass<T>): String = "KLogger"
}