package org.danbrough.klog

import kotlin.reflect.KClass


@Suppress("NOTHING_TO_INLINE")
actual inline fun platformStatementContext(): StatementContext =
  StatementContext("native", platform.posix.pthread_self().toLong())


//actual fun getTimeMillis(): Long = org.danbrough.klog.posix.getTimeMillisTest().toLong()
actual fun getTimeMillis(): Long = -1

//actual fun getTimeMillis(): Long = -1


private class NativeLogRegistry : DefaultLogRegistry() {
}

private
var registry: DefaultLogRegistry? = null

actual fun createKLogRegistry(): KLogRegistry = registry ?: NativeLogRegistry().also {
  registry = it
}

actual fun KClass<*>.klogName(): String = qualifiedName!!.removeSuffix(".Companion")
