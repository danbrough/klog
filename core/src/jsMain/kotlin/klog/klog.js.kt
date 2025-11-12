package klog

import klog.stdout.StdoutLogging
import klog.stdout.StdoutMessageFormatter
import klog.stdout.colorString
import klog.stdout.defaultMessageFormatter
import kotlin.reflect.KClass

object JsLogFactory : KLogFactory() {

  var formatter: StdoutMessageFormatter = defaultMessageFormatter


  private var log: LoggerMethod = { level, name, message, t ->
    console.log(StdoutLogging.formatter.invoke(level, name, message))
    if (t != null) console.log(colorString(Logger.Level.ERROR, t.stackTraceToString()))
  }

  override fun logger(logName: String) = LoggerImpl(logName, log)
}

internal actual fun klogDefaultFactory(): KLogFactory = JsLogFactory

actual fun <T : Any> loggerName(clazz: KClass<T>): String = "KOTLIN"