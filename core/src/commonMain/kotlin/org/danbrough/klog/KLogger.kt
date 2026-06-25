package org.danbrough.klog


enum class Level {
  TRACE, DEBUG, INFO, WARN, ERROR, NONE;

}

typealias MessageBlock = () -> Any?

open class KLogger(
  val name: String = "", val level: Level = Level.TRACE, val conf: KLogConfiguration
) {


  fun copy(
    name: String, level: Level = this.level, conf: KLogConfiguration = this.conf
  ) = KLogger(name, level, conf)


  inline fun verbose(t: Throwable? = null, message: MessageBlock) = trace(t, message)

  inline fun v(t: Throwable? = null, message: MessageBlock) = trace(t, message)
  inline fun trace(t: Throwable? = null, message: MessageBlock) =
    if (level <= Level.TRACE) conf.write(Level.TRACE, name, message, t) else Unit

  inline fun d(t: Throwable? = null, message: MessageBlock) = debug(t, message)
  inline fun debug(t: Throwable? = null, message: MessageBlock) =
    if (level <= Level.DEBUG) conf.write(Level.DEBUG, name, message, t) else Unit

  inline fun i(t: Throwable? = null, message: MessageBlock) = info(t, message)

  inline fun info(t: Throwable? = null, message: MessageBlock) =
    if (level <= Level.INFO) conf.write(Level.INFO, name, message, t) else Unit

  inline fun w(t: Throwable? = null, message: MessageBlock) = warn(t, message)
  inline fun warn(t: Throwable? = null, message: MessageBlock) =
    if (level <= Level.WARN) conf.write(Level.WARN, name, message, t) else Unit

  inline fun e(t: Throwable? = null, message: MessageBlock) = error(t, message)
  inline fun error(t: Throwable? = null, message: MessageBlock) =
    if (level <= Level.ERROR) conf.write(Level.ERROR, name, message, t) else Unit


}



