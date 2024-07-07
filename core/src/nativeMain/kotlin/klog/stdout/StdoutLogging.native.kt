@file:OptIn(ExperimentalForeignApi::class)

package klog.stdout

import kotlinx.cinterop.ExperimentalForeignApi
import platform.posix.stderr

val printMethodStderr: PrintMethod = {
  platform.posix.fprintf(stderr, it?.toString())
}

actual fun printMethodStderr(): PrintMethod = printMethodStderr
