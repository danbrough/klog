package org.danbrough.klog

import kotlin.reflect.KClass


actual fun platformLogMessageContext(): KLogMessageContext =
  KLogMessageContextImpl("js", 1)


actual fun createKLogRegistry(): KLogRegistry = object : DefaultLogRegistry() {

}


actual fun KClass<*>.klogName():String = simpleName!!

actual fun getTimeMillis(): Long = -1


