package klog

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging

object OshaiLoggingFactory : KLogFactory() {

  class OshaiLogger(override val name: String) : DelegatingLogger {
    private val logger: KLogger = KotlinLogging.logger(name)

    override var log: LoggerMethod = { level, _, message, t ->
      when (level) {
        Logger.Level.TRACE -> logger.trace(t, message)
        Logger.Level.DEBUG -> logger.debug(t, message)
        Logger.Level.INFO -> logger.info(t, message)
        Logger.Level.WARN -> logger.warn(t, message)
        Logger.Level.ERROR -> logger.error(t, message)
        Logger.Level.NONE -> {}
      }
    }
    override var level: Logger.Level = Logger.Level.TRACE
  }

  override fun logger(logName: String) = OshaiLogger(logName)

}

fun kloggingOshai(block: OshaiLoggingFactory.() -> Unit = {}) {
  installLogging(OshaiLoggingFactory, block)
}