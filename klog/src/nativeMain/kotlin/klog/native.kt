package klog

import platform.posix.pthread_self


object NativeLogFactory : BaseLogFactory() {
  override fun logEntryContext() = LogEntryContext("native", pthread_self().toLong())
}

actual fun logFactory(): KLogFactory = NativeLogFactory

