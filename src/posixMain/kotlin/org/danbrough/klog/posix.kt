package org.danbrough.klog

import platform.posix.pthread_self
import kotlin.reflect.KClass


@Suppress("NOTHING_TO_INLINE")
actual inline fun platformStatementContext(): StatementContext =
  StatementContext("native", -1L)


actual fun getTimeMillis(): Long = org.danbrough.klog.posix.timeInMillisSinceEpoch().toLong()


actual fun getThreadID(): Long = -1L

private object NativeRegistry : DefaultLogRegistry()

actual fun createKLogRegistry(): KLogRegistry = NativeRegistry.also {
  println("PLATFORM NAME: ${Platform.osFamily.name}")
}


actual fun KClass<*>.klogName(): String = qualifiedName!!.removeSuffix(".Companion")
