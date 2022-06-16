package org.danbrough.klog


actual fun platformLogMessageContext(): KLogMessageContext = jvmLogMessageContext()

actual fun createKLogRegistry(): KLogRegistry = object : DefaultLogRegistry() {

}


