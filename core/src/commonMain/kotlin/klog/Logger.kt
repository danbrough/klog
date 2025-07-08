package klog

import klog.Logger.Level

typealias LoggerMethod = (level: Level, name: String, message: () -> Any?, t: Throwable?) -> Unit

interface Logger {

  val name: String

  enum class Level {
    TRACE, DEBUG, INFO, WARN, ERROR, NONE;
  }

  fun trace(t: Throwable? = null, message: () -> Any?)
  fun trace(t: Throwable? = null, message: suspend () -> Any?)

  fun debug(t: Throwable? = null, message: () -> Any?)
  fun info(t: Throwable? = null, message: () -> Any?)
  fun warn(t: Throwable? = null, message: () -> Any?)
  fun error(t: Throwable? = null, message: () -> Any?)
}

interface DelegatingLogger : Logger {

  var log: LoggerMethod

  override fun trace(t: Throwable?, message: () -> Any?) =
    log.invoke(Level.TRACE, name, message, t)

  override fun trace(t: Throwable?, message: suspend () -> Any?) = trace(t, runBlocking(message))

  override fun debug(t: Throwable?, message: () -> Any?) =
    log.invoke(Level.DEBUG, name, message, t)

  override fun info(t: Throwable?, message: () -> Any?) =
    log.invoke(Level.INFO, name, message, t)

  override fun warn(t: Throwable?, message: () -> Any?) =
    log.invoke(Level.WARN, name, message, t)

  override fun error(t: Throwable?, message: () -> Any?) =
    log.invoke(Level.ERROR, name, message, t)
}


class LoggerImpl(override val name: String, override var log: LoggerMethod) : DelegatingLogger

