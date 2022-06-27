package org.danbrough.klog

import platform.posix.pthread_self
import kotlin.reflect.KClass


@Suppress("NOTHING_TO_INLINE")
actual inline fun platformStatementContext(): StatementContext =
  StatementContext("native", pthread_self().toLong())


actual fun getTimeMillis(): Long = org.danbrough.klog.posix.timeInMillisSinceEpoch().toLong()

private object NativeRegistry : DefaultLogRegistry()

actual fun createKLogRegistry(): KLogRegistry = NativeRegistry


actual fun KClass<*>.klogName(): String = qualifiedName!!.removeSuffix(".Companion")
