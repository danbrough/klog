package klog


actual fun platformLogMessageContext(): LogMessageContext = jvmLogMessageContext()

actual fun createKogRegistry(): KLogRegistry = object : DefaultLogRegistry() {

}


