package org.danbrough.klog

import org.danbrough.klog.posix.threadID
import org.danbrough.klog.posix.timeInMillisSinceEpoch
import kotlin.reflect.KClass


@Suppress("NOTHING_TO_INLINE")
actual inline fun platformStatementContext(): StatementContext =
  StatementContext("native", threadID().toString())


actual fun getTimeMillis(): Long = timeInMillisSinceEpoch().toLong()

object PosixKLogRegistry : DefaultLogRegistry()

actual fun createKLogRegistry(): KLogRegistry = PosixKLogRegistry


actual fun KClass<*>.klogName(): String = qualifiedName!!.removeSuffix(".Companion")
