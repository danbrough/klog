package klog

import kotlin.reflect.KClass

actual fun logEntryContext(): LogEntryContext =
  LogEntryContext("todo", -1L, -1, null, null)

private object AndroidLogFactory : KLogFactory() {
  //override fun <T : Any> getLog(clazz: KClass<T>): KLog = rootLog
}

actual fun klogFactory(): KLogFactory = AndroidLogFactory