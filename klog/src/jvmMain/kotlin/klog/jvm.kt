package klog


actual fun platformLogMessageContext(): LogMessageContext = jvmLogMessageContext()

private object JvmLogFactory : DefaultLogRegistry() {
}

actual fun klogRegistry(): KLogRegistry = JvmLogFactory


