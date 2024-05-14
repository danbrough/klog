package klog

import org.slf4j.Logger
import org.slf4j.LoggerFactory


private class Slf4jLog(private val logger: Logger) : DelegatingLogger {

  override val name: String = logger.name

  override var log: LoggerMethod = { level, _, message, t ->
    when (level) {
      klog.Logger.Level.TRACE -> if (logger.isTraceEnabled) logger.trace(message(), t)
      klog.Logger.Level.DEBUG -> if (logger.isDebugEnabled) logger.debug(message(), t)
      klog.Logger.Level.INFO -> if (logger.isInfoEnabled) logger.info(message(), t)
      klog.Logger.Level.WARN -> if (logger.isWarnEnabled) logger.warn(message(), t)
      klog.Logger.Level.ERROR -> if (logger.isErrorEnabled) logger.error(message(), t)
      klog.Logger.Level.NONE -> Unit
    }
  }
}

object Slf4jLogging : KLogFactory() {
  override fun logger(logName: String): klog.Logger =
    Slf4jLog(LoggerFactory.getLogger(logName))
}


fun kloggingSlf4j(block: Slf4jLogging.() -> Unit = {}) {
  installLogging(Slf4jLogging, block)
}
