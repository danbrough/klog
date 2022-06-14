package klog

private object JSLogFactory : BaseLogFactory() {
  override fun logEntryContext() = LogEntryContext("js", 1)


}

actual fun logFactory(): KLogFactory = JSLogFactory


