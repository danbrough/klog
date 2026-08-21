package org.danbrough.klog

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toKString
import platform.posix.fprintf
import platform.posix.getenv
import platform.posix.pthread_self
import platform.posix.stderr
import platform.posix.stdout
import kotlin.reflect.KClass

open class UtilsPosix : KLogUtils {
  @OptIn(ExperimentalForeignApi::class)
  override val environment: Map<String, String?> =
    object : Map<String, String?> by emptyMap() {
      override fun containsKey(key: String): Boolean = getenv(key) != null
      override fun get(key: String): String? = getenv(key)?.toKString()
    }

  //

  override fun getThreadName(): String = pthread_self().toString()

  @OptIn(ExperimentalForeignApi::class)
  override val stderrPrinter: Printer = {
    fprintf(stderr, "${it?.toString()}\n")
  }

  @OptIn(ExperimentalForeignApi::class)
  override val stdoutPrinter: Printer = {
    fprintf(stdout, "${it?.toString()}\n")
  }

  override fun <T : Any> loggerName(clazz: KClass<T>): String =
    clazz.qualifiedName!!.substringBefore(".Companion")

}