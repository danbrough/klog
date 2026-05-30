@file:Suppress("MemberVisibilityCanBePrivate")

package org.danbrough.klog.std

import org.danbrough.klog.CachingPropertyResolver
import org.danbrough.klog.EnvPropertyResolver
import org.danbrough.klog.KLogFactory
import org.danbrough.klog.Level
import org.danbrough.klog.Utils


typealias StdoutMessageFormatter = BaseStandardLogFactory.(level: Level, name: String, message: String?) -> String

typealias Printer = (Any?) -> Unit

var colorString: BaseStandardLogFactory.(level: Level, s: String) -> String =
  { level, s -> if (coloredOutput) level.colored(s) else s }

val defaultMessageFormatter: StdoutMessageFormatter = { level, name, message ->
  colorString(
    level,
    message.toString()
  )
}


open class BaseStandardLogFactory : KLogFactory() {

  val propertyResolver: CachingPropertyResolver<Level> = EnvPropertyResolver
  override var defaultLogLevel: Level = propertyResolver.resolve("KLOG_LEVEL") ?: Level.TRACE

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

  fun getLogLevel(logName: String): Level = propertyResolver.resolve(logName) ?: defaultLogLevel

  override fun logger(logName: String) = TODO() //LoggerBase(logName, log, getLogLevel(logName))
}

object StandardLogFactory : BaseStandardLogFactory()