@file:Suppress("MemberVisibilityCanBePrivate")

package org.danbrough.klog.std

import org.danbrough.klog.KLogConfiguration
import org.danbrough.klog.KLogFactory
import org.danbrough.klog.KLogWriter
import org.danbrough.klog.KLogger
import org.danbrough.klog.Level
import org.danbrough.klog.Utils
import org.danbrough.klog.cached
import org.danbrough.klog.propertyResolver


typealias Printer = (Any?) -> Unit

fun KLogConfiguration.colorString(level: Level, message: String): String =
  if (coloredOutput) level.colored(message) else message


val StdLogWriters = listOf(object : KLogWriter {
  override fun writeLog(
    conf: KLogConfiguration, level: Level, name: String, message: String, t: Throwable?
  ) {
    println(conf.colorString(level, "$level $name $message conf: $conf"))
  }
})
internal val logLevels =
  propertyResolver("_") { name -> Utils.environment[name]?.let { Level.valueOf(it) } }.cached()


internal val StdKLogConfiguration = KLogConfiguration(StdLogWriters)

open class StandardLogFactory : KLogFactory() {
  override val defaultLogLevel: Level = logLevels["KLOG_LEVEL"] ?: Level.ERROR
  fun getLogLevel(logName: String): Level = logLevels[logName] ?: defaultLogLevel

  override fun logger(logName: String) =
    KLogger(logName, getLogLevel(logName), StdKLogConfiguration)
}

