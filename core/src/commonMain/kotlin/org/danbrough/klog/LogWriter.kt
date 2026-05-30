package org.danbrough.klog

//typealias LogWriter = (level: Level, name: String, message: String, t: Throwable?) -> Unit
abstract class BaseLogWriter {
  abstract fun writeLog(
    logger: LoggerBase, level: Level, name: String, message: String, t: Throwable?
  )
}