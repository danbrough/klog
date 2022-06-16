package org.danbrough.klog

import org.danbrough.klog.jvmLogMessageContext
import org.danbrough.klog.DefaultLogRegistry
import org.danbrough.klog.KLogRegistry
import org.danbrough.klog.LogMessageContext


actual fun platformLogMessageContext(): LogMessageContext = jvmLogMessageContext()

actual fun createKLogRegistry(): KLogRegistry = object : DefaultLogRegistry() {

}


