package klog

import platform.posix.pthread_self

object NativeLogFactory : BaseLogFactory() {
/*  override var rootLogger: KLog =
    KLogImpl(Level.TRACE, LogFormatters.colored(LogFormatters.simple), LogWriters.stdOut)*/

  override fun logEntryContext() = LogEntryContext("native", pthread_self().toLong())

}

actual fun logFactory(): KLogFactory = NativeLogFactory

