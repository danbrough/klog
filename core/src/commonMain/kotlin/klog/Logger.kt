package klog

import klog.Logger.Level

typealias LoggerMethod = Logger.(level: Level, name: String, message: () -> Any?, t: Throwable?) -> Unit

interface Logger {

  val name: String

  var level: Level

  enum class Level {
    TRACE, DEBUG, INFO, WARN, ERROR, NONE;
  }

  fun trace(t: Throwable? = null, message: () -> Any?)
  fun debug(t: Throwable? = null, message: () -> Any?)
  fun info(t: Throwable? = null, message: () -> Any?)
  fun warn(t: Throwable? = null, message: () -> Any?)
  fun error(t: Throwable? = null, message: () -> Any?)
}

interface DelegatingLogger : Logger {

  var log: LoggerMethod

  override fun trace(t: Throwable?, message: () -> Any?) =
    if (level <= Level.TRACE) log.invoke(this, Level.TRACE, name, message, t) else Unit

  override fun debug(t: Throwable?, message: () -> Any?) =
    if (level <= Level.DEBUG) log.invoke(this, Level.DEBUG, name, message, t) else Unit

  override fun info(t: Throwable?, message: () -> Any?) =
    if (level <= Level.INFO) log.invoke(this, Level.INFO, name, message, t) else Unit

  override fun warn(t: Throwable?, message: () -> Any?) =
    if (level <= Level.WARN) log.invoke(this, Level.WARN, name, message, t) else Unit

  override fun error(t: Throwable?, message: () -> Any?) =
    if (level <= Level.ERROR) log.invoke(this, Level.ERROR, name, message, t) else Unit


}

object NOOPLogger : DelegatingLogger {
  override var log: LoggerMethod = { _, _, _, _ -> }
  override var level: Level = Level.NONE
  override val name: String = ""
}


open class LoggerImpl(
  override val name: String,
  override var log: LoggerMethod,
  override var level: Level,
) : DelegatingLogger

