package org.danbrough.klog

import kotlinx.cinterop.UnsafeNumber
import platform.posix.pthread_self
import kotlin.reflect.KClass


@Suppress("NOTHING_TO_INLINE")
@OptIn(UnsafeNumber::class)
actual inline fun platformStatementContext(): StatementContext =
  StatementContext("native", pthread_self().toLong())


actual fun getTimeMillis(): Long = org.danbrough.klog.native.getTimeMillisTest().toLong()


private class NativeLogRegistry : DefaultLogRegistry() {
}

private var registry: DefaultLogRegistry? = null

actual fun createKLogRegistry(): KLogRegistry = registry ?: NativeLogRegistry().also {
  registry = it
}

actual fun KClass<*>.klogName(): String = qualifiedName!!.removeSuffix(".Companion")
