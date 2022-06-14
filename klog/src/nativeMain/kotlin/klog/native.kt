package klog

import kotlinx.cinterop.UnsafeNumber
import platform.posix.pthread_self
import kotlin.reflect.KClass


@OptIn(UnsafeNumber::class)
actual fun logEntryContext(): LogEntryContext = LogEntryContext("native", pthread_self().toLong())


private class NativeLogFactory : KLogFactory() {
  override fun <T : Any> getLog(clazz: KClass<T>): KLog = rootLog
}

private var logFactory: KLogFactory? = null

actual fun klogFactory(): KLogFactory = logFactory ?: NativeLogFactory().also {
  logFactory = it
}