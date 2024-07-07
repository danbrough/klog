@file:OptIn(ExperimentalForeignApi::class)

package klog.stdout

import kotlinx.cinterop.ExperimentalForeignApi

val printMethodStderr: PrintMethod = {
  platform.posix.fprintf(stderr, it?.toString())
}

expect fun printMethodStderr(): PrintMethod = printMethodStderr
