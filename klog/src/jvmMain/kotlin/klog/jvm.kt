package klog


private object JvmLogFactory : BaseLogFactory() {
  override fun logEntryContext() = Thread.currentThread().let {
    LogEntryContext(it.name, it.id)
  }

}

actual fun logFactory(): KLogFactory = JvmLogFactory