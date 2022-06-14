package klog


private object AndroidLogFactory : BaseLogFactory() {
  override fun logEntryContext(): LogEntryContext = LogEntryContext("todo",-1L,-1,null,null)
}

actual fun logFactory(): KLogFactory = AndroidLogFactory