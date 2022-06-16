package org.danbrough.klog

actual fun platformLogMessageContext(): LogMessageContext = jvmLogMessageContext()

actual fun createKLogRegistry(): KLogRegistry = object : DefaultLogRegistry() {}