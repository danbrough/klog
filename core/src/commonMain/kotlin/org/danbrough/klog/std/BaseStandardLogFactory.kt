@file:Suppress("MemberVisibilityCanBePrivate")

package org.danbrough.klog.std

import org.danbrough.klog.KLogFactory
import org.danbrough.klog.KLogger
import org.danbrough.klog.Level
import org.danbrough.klog.Utils
import org.danbrough.klog.cached
import org.danbrough.klog.propertyResolver
import org.danbrough.klog.withSuffix


typealias StdoutMessageFormatter = BaseStandardLogFactory.(level: Level, name: String, message: String?) -> String

typealias Printer = (Any?) -> Unit

var colorString: BaseStandardLogFactory.(level: Level, s: String) -> String =
  { level, s -> if (coloredOutput) level.colored(s) else s }

val defaultMessageFormatter: StdoutMessageFormatter = { level, name, message ->
  colorString(
    level, message.toString()
  )
}

internal val logLevels =
  propertyResolver("_") { name -> Utils.environment[name]?.let { Level.valueOf(it) } }
    .cached()

open class BaseStandardLogFactory : KLogFactory() {

  override var defaultLogLevel: Level = logLevels["KLOG_LEVEL"] ?: Level.ERROR

  var coloredOutput: Boolean = true

  var useStderr: Boolean = false
    set(value) {
      field = value
      if (value) printer = Utils.stderrPrinter else Utils.stdoutPrinter
    }

  var printer: Printer = Utils.stdoutPrinter

  var formatter: StdoutMessageFormatter = defaultMessageFormatter

  /*  var log: LogWriter = { level, name, message, t ->
      printer(formatter.invoke(this@StandardLogging, level, name, message))
      if (t != null) printer(colorString(Level.ERROR, t.stackTraceToString()))
    }*/

  fun getLogLevel(logName: String): Level = logLevels[logName] ?: defaultLogLevel

  override fun logger(logName: String) = KLogger(logName, getLogLevel(logName))
}

/*object StdLogWriter : KLogWriter {
  override fun writeLog(
    logger: KLogger,
    level: Level,
    name: String,
    message: String,
    t: Throwable?
  ) {
    println(this@BaseStandardLogFactory, level, name, message)
  }
}*/

//object StandardLogFactory : BaseStandardLogFactory()