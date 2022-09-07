package klog

import klog.posix.threadID
import klog.posix.timeInMillisSinceEpoch
import kotlin.native.concurrent.ThreadLocal
import kotlin.reflect.KClass


@Suppress("NOTHING_TO_INLINE")
actual inline fun platformStatementContext(): StatementContext =
  StatementContext("native", threadID().toString())


actual fun getTimeMillis(): Long = timeInMillisSinceEpoch().toLong()

@ThreadLocal
object PosixKLogRegistry : DefaultLogFactory()

actual fun createKLogRegistry(): KLogFactory = PosixKLogRegistry


actual fun KClass<*>.klogName(): String = qualifiedName!!.removeSuffix(".Companion")

