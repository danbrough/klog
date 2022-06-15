package klog

import kotlinx.cinterop.UnsafeNumber
import platform.posix.pthread_self


@OptIn(UnsafeNumber::class)
actual fun platformLogMessageContext(): LogMessageContext =
  LogMessageContextImpl("native", pthread_self().toLong())


private class NativeLogFactory : DefaultLogRegistry() {
}

private var registry: DefaultLogRegistry? = null

actual fun klogRegistry(): KLogRegistry = registry ?: NativeLogFactory().also {
  registry = it
}