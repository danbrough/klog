package klog

import klog.Logger.Level
import klog.stdout.StdoutLogging
import klog.stdout.StdoutMessageFormatter
import klog.stdout.colorString
import klog.stdout.defaultMessageFormatter
import kotlin.reflect.KClass

object JsLogFactory : KLogFactory() {

  var formatter: StdoutMessageFormatter = defaultMessageFormatter


  val stdoutLogging = StdoutLogging()
  private var log: LoggerMethod = { level, name, message, t ->
    console.log(stdoutLogging.formatter.invoke(stdoutLogging, level, name, message))
    if (t != null) console.log(stdoutLogging.colorString(Level.ERROR, t.stackTraceToString()))
  }

  override fun logger(logName: String) = LoggerImpl(logName, log, Level.TRACE)
}

internal actual fun klogDefaultFactory(): KLogFactory = JsLogFactory

actual fun <T : Any> loggerName(clazz: KClass<T>): String = "KOTLIN"

actual fun getEnv(name: String): String? = null