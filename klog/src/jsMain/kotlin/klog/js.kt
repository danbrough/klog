package klog

import kotlin.reflect.KClass


actual fun logEntryContext(): LogEntryContext = LogEntryContext("js", 1)

private object JsLogFactory : KLogFactory(){
 // private var rootLogger = KLogImpl(Level.TRACE,LogFormatters.simple,LogWriters.stdOut,null)
  override fun <T : Any> getLog(clazz: KClass<T>): KLog = rootLog

}
actual fun klogFactory(): KLogFactory = JsLogFactory