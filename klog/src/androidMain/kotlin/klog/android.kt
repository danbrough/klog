package klog

actual fun platformLogMessageContext(): LogMessageContext = jvmLogMessageContext()

private object AndroidLogFactory : DefaultLogRegistry() {
  //override fun <T : Any> getLog(clazz: KClass<T>): KLog = rootLog
}

actual fun klogRegistry(): KLogRegistry = AndroidLogFactory