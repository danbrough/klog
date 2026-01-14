@file:Suppress("MemberVisibilityCanBePrivate")

package klog.stdout

import klog.CachingPropertyResolver
import klog.EnvPropertyResolver
import klog.KLogFactory
import klog.Logger
import klog.Logger.Level
import klog.LoggerImpl
import klog.LoggerMethod


typealias StdoutMessageFormatter = StdoutLogging.(level: Level, name: String, message: () -> Any?) -> String

typealias Printer = (Any?) -> Unit

var colorString: StdoutLogging.(level: Level, s: String) -> String =
  { level, s -> if (coloredOutput) level.colored(s) else s }

val defaultMessageFormatter: StdoutMessageFormatter = { level, name, message ->
  colorString(level, "$level:$name: ${message()}")
}

expect fun printMethodStderr(): Printer

val stderrPrinter: Printer = printMethodStderr()
val stdoutPrinter: Printer = ::println

class StdoutLogging(
  override var defaultLogLevel: Level = EnvPropertyResolver.resolve("KLOG_LEVEL") ?: Level.NONE,
  val propertyResolver: CachingPropertyResolver<Level> = EnvPropertyResolver
) : KLogFactory() {

  var coloredOutput: Boolean = true

  var useStderr: Boolean = false
    set(value) {
      field = value
      if (value) printer = stderrPrinter else stdoutPrinter
    }

  var printer: Printer = stdoutPrinter

  var formatter: StdoutMessageFormatter = defaultMessageFormatter

  var log: LoggerMethod = { level, name, message, t ->
    printer(formatter.invoke(this@StdoutLogging, level, name, message))
    if (t != null) printer(colorString(Level.ERROR, t.stackTraceToString()))
  }

  fun getLogLevel(logName: String): Level = propertyResolver.resolve(logName) ?: defaultLogLevel

  override fun logger(logName: String) = LoggerImpl(logName, log, getLogLevel(logName))
}