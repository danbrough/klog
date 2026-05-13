package org.danbrough.klog

import org.danbrough.klog.stdout.Printer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toKString
import org.danbrough.klog.stdout.StdoutLogging
import platform.posix.fprintf
import platform.posix.getenv
import platform.posix.pthread_self
import platform.posix.stderr
import platform.posix.stdout
import kotlin.reflect.KClass

actual object Utils : KLogUtils() {
  @OptIn(ExperimentalForeignApi::class)
  actual override val environment: Map<String, String?> = object : Map<String, String?> by emptyMap() {
    override fun get(key: String): String? = getenv(key)?.toKString()
  }


  //

  actual override fun getThreadName(): String = pthread_self().toString()

  @OptIn(ExperimentalForeignApi::class)
  actual override val stderrPrinter: Printer = {
    fprintf(stderr, "${it?.toString()}\n")
  }

  @OptIn(ExperimentalForeignApi::class)
  actual override val stdoutPrinter: Printer = {
    fprintf(stdout, "${it?.toString()}\n")
  }

  actual override fun <T : Any> loggerName(clazz: KClass<T>): String =
    clazz.qualifiedName!!.substringBefore(".Companion")

}