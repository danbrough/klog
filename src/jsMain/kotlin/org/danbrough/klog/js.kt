package org.danbrough.klog

import org.danbrough.klog.DefaultLogRegistry
import org.danbrough.klog.KLogRegistry
import org.danbrough.klog.LogMessageContext
import org.danbrough.klog.LogMessageContextImpl
import kotlin.reflect.KClass


actual fun platformLogMessageContext(): LogMessageContext =
  LogMessageContextImpl("js", 1)


actual fun createKLogRegistry(): KLogRegistry = object : DefaultLogRegistry() {

}


actual fun KClass<*>.klogName():String = simpleName!!

actual fun getTimeMillis(): Long = -1


