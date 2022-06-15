package klog


actual fun platformLogMessageContext(): LogMessageContext =
  LogMessageContextImpl("js", 1)

private object JsLogFactory : DefaultLogRegistry() {
  // private var rootLogger = KLogImpl(Level.TRACE,LogFormatters.simple,LogWriters.stdOut,null)

}

actual fun klogRegistry(): KLogRegistry = JsLogFactory