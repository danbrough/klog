package org.danbrough.klog


typealias MessageBlock = () -> Any?

enum class Level {
  TRACE, DEBUG, INFO, WARN, ERROR, NONE;
}

open class LoggerBase(val name: String = "") {

  var logWriters: MutableList<BaseLogWriter> = mutableListOf()

  open var level: Level = Level.TRACE

  inline fun write(level: Level, name: String, message: MessageBlock, t: Throwable? = null) {
    val messageString = message().toString()
    logWriters.forEach {
      it.writeLog(this, level, name, messageString, t)
    }
  }

  inline fun verbose(t: Throwable? = null, message: MessageBlock) = trace(t, message)

  inline fun v(t: Throwable? = null, message: MessageBlock) = trace(t, message)
  inline fun trace(t: Throwable? = null, message: MessageBlock) =
    if (level <= Level.TRACE) write(Level.TRACE, name, message, t) else Unit

  inline fun d(t: Throwable? = null, message: MessageBlock) = debug(t, message)
  inline fun debug(t: Throwable? = null, message: MessageBlock) =
    if (level <= Level.DEBUG) write(Level.DEBUG, name, message, t) else Unit

  inline fun i(t: Throwable? = null, message: MessageBlock) = info(t, message)

  inline fun info(t: Throwable? = null, message: MessageBlock) =
    if (level <= Level.INFO) write(Level.INFO, name, message, t) else Unit

  inline fun w(t: Throwable? = null, message: MessageBlock) = warn(t, message)
  inline fun warn(t: Throwable? = null, message: MessageBlock) =
    if (level <= Level.WARN) write(Level.WARN, name, message, t) else Unit

  inline fun e(t: Throwable? = null, message: MessageBlock) = error(t, message)
  inline fun error(t: Throwable? = null, message: MessageBlock) =
    if (level <= Level.ERROR) write(Level.ERROR, name, message, t) else Unit

}

object NOOPLogger : LoggerBase()

