@file:Suppress("MemberVisibilityCanBePrivate")

package org.danbrough.klog.std

import org.danbrough.klog.KLogFactory
import org.danbrough.klog.Level
import org.danbrough.klog.LogWriter
import org.danbrough.klog.LoggerBase
import org.danbrough.klog.Utils
import org.danbrough.klog.cached
import org.danbrough.klog.propertyResolver


typealias StdoutMessageFormatter = BaseStandardLogFactory.(level: Level, name: String, message: String?) -> String

typealias Printer = (Any?) -> Unit

var colorString: BaseStandardLogFactory.(level: Level, s: String) -> String =
  { level, s -> if (coloredOutput) level.colored(s) else s }

val defaultMessageFormatter: StdoutMessageFormatter = { level, name, message ->
  colorString(
    level, message.toString()
  )
}


open class BaseStandardLogFactory : KLogFactory() {

  val logLevels =
    propertyResolver(getter = { name -> Utils.environment[name]?.let { Level.valueOf(it) } }).cached()

  override var defaultLogLevel: Level = logLevels["KLOG"] ?: Level.TRACE

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

  override fun logger(logName: String) = LoggerBase(logName, getLogLevel(logName)).apply {
    logWriters.add(object : LogWriter {
      override fun writeLog(
        logger: LoggerBase,
        level: Level,
        name: String,
        message: String,
        t: Throwable?
      ) {
        println(formatter.invoke(this@BaseStandardLogFactory, level, name, message))
      }
    })
  }
}

object StandardLogFactory : BaseStandardLogFactory()