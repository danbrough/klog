@file:Suppress("MemberVisibilityCanBePrivate")

package org.danbrough.klog.stdout

import org.danbrough.klog.*
import org.danbrough.klog.Logger.Level


typealias StdoutMessageFormatter = StdoutLogging.(level: Level, name: String, message: () -> Any?) -> String

typealias Printer = (Any?) -> Unit

var colorString: StdoutLogging.(level: Level, s: String) -> String =
  { level, s -> if (coloredOutput) level.colored(s) else s }

val defaultMessageFormatter: StdoutMessageFormatter = { level, name, message ->
  colorString(
    level,
    "${level.toString().let { if (it.length == 4) "$it " else it }}:$name: ${message()}"
  )
}


open class StdoutLogging(
  override var defaultLogLevel: Level = EnvPropertyResolver.resolve("KLOG_LEVEL") ?: Level.NONE,
  val propertyResolver: CachingPropertyResolver<Level> = EnvPropertyResolver
) : KLogFactory() {

  var coloredOutput: Boolean = true

  var useStderr: Boolean = false
    set(value) {
      field = value
      if (value) printer = Utils.stderrPrinter else Utils.stdoutPrinter
    }

  var printer: Printer = Utils.stdoutPrinter

  var formatter: StdoutMessageFormatter = defaultMessageFormatter

  var log: LoggerMethod = { level, name, message, t ->
    printer(formatter.invoke(this@StdoutLogging, level, name, message))
    if (t != null) printer(colorString(Level.ERROR, t.stackTraceToString()))
  }

  fun getLogLevel(logName: String): Level = propertyResolver.resolve(logName) ?: defaultLogLevel

  override fun logger(logName: String) = LoggerImpl(logName, log, getLogLevel(logName))
}