package klog

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toKString
import platform.posix.pthread_self

actual object Utils {
  @OptIn(ExperimentalForeignApi::class)
  actual fun getEnv(name: String): String? = platform.posix.getenv(name)?.toKString()

  actual fun getThreadName(): String = pthread_self().toString()

}