package org.danbrough.klog

import platform.posix.pthread_self
import kotlin.reflect.KClass


@Suppress("NOTHING_TO_INLINE")
actual inline fun platformStatementContext(): StatementContext =
  StatementContext("native", -1L)


actual fun getTimeMillis(): Long = org.danbrough.klog.posix.timeInMillisSinceEpoch().toLong()

private val log = klog("org.danbrough.klog")


//actual fun getTimeMillis(): Long = -1


internal class NativeLogRegistry : DefaultLogRegistry() {
}

/*
private
var registry: DefaultLogRegistry? = null
*/

private val nativeLogRegistry by lazy {
  println("Creating NativeLogRegistry")
  NativeLogRegistry()
}

actual fun createKLogRegistry(): KLogRegistry = nativeLogRegistry
actual fun KClass<*>.klogName(): String = qualifiedName!!.removeSuffix(".Companion")
