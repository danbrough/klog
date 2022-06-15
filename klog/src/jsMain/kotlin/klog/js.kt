package klog

import kotlin.reflect.KClass


actual fun platformLogMessageContext(): LogMessageContext =
  LogMessageContextImpl("js", 1)


actual fun createKogRegistry(): KLogRegistry = object : DefaultLogRegistry() {

}


actual fun KClass<*>.name():String = simpleName!!
