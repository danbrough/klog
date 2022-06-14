package klog

import kotlin.reflect.KClass


actual fun logEntryContext(): LogEntryContext = Thread.currentThread().let {
  LogEntryContext(it.name, it.id)
}

private object JvmLogFactory : KLogFactory() {
  override fun <T : Any> getLog(clazz: KClass<T>): KLog = rootLog
}

actual fun klogFactory(): KLogFactory = JvmLogFactory


