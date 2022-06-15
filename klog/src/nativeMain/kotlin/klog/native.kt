package klog

import kotlinx.cinterop.UnsafeNumber
import platform.posix.pthread_self
import kotlin.reflect.KClass


@OptIn(UnsafeNumber::class)
actual fun platformLogMessageContext(): LogMessageContext =
  LogMessageContextImpl("native", pthread_self().toLong())


private class NativeLogRegistry : DefaultLogRegistry() {
}

private var registry: DefaultLogRegistry? = null

actual fun createKogRegistry(): KLogRegistry = registry ?: NativeLogRegistry().also {
  registry = it
}

actual fun KClass<*>.name():String = qualifiedName!!
