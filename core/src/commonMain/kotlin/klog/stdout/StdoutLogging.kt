@file:Suppress("MemberVisibilityCanBePrivate")

package klog.stdout

import klog.KLogFactory
import klog.Logger
import klog.LoggerImpl
import klog.LoggerMethod


typealias StdoutMessageFormatter = (level: Logger.Level, name: String, message: () -> Any?) -> String

typealias PrintMethod = (Any?) -> Unit


var colorString: (level: Logger.Level, s: String) -> String =
  { level, s -> if (StdoutLogging.coloredOutput) level.colored(s) else s }

val defaultMessageFormatter: StdoutMessageFormatter = { level, name, message ->
  colorString(level, "$level:$name: ${message()}")
}

expect fun printMethodStderr(): PrintMethod

val printMethodStderr: PrintMethod = printMethodStderr()
val printMethodStdout: PrintMethod = ::println

object StdoutLogging : KLogFactory() {
  var coloredOutput: Boolean = true
  var useStderr: Boolean = false
    set(value) {
      field = value
      if (value) printMethod = printMethodStderr else printMethodStdout
    }

  var printMethod: PrintMethod = printMethodStdout

  var formatter: StdoutMessageFormatter = defaultMessageFormatter

  var log: LoggerMethod = { level, name, message, t ->
    println(formatter.invoke(level, name, message))

    if (t != null) println(colorString(Logger.Level.ERROR, t.stackTraceToString()))
  }

  override fun logger(logName: String) = LoggerImpl(logName, log)
}