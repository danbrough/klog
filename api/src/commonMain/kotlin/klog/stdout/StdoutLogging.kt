@file:Suppress("MemberVisibilityCanBePrivate")

package klog.stdout

import klog.KLogFactory
import klog.Logger
import klog.LoggerImpl
import klog.LoggerMethod


typealias StdoutMessageFormatter = (level: Logger.Level, name: String, message: () -> String) -> String

var coloredOutput: Boolean = true

var colorString: (level: Logger.Level, s: String) -> String =
  { level, s -> if (coloredOutput) level.colored(s) else s }

val defaultMessageFormatter: StdoutMessageFormatter = { level, name, message ->
  colorString(level, "$level:$name: ${message()}==")
}

object StdoutLogging : KLogFactory() {

  var formatter: StdoutMessageFormatter = defaultMessageFormatter

  var log: LoggerMethod = { level, name, message, t ->
    println(formatter.invoke(level, name, message))
    if (t != null) println(colorString(Logger.Level.ERROR, t.stackTraceToString()))
  }

  override fun logger(logName: String) = LoggerImpl(logName, log)
}