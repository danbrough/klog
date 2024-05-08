package klog

object OshaiLoggingFactory : KLogFactory() {
	class Logger(override val name: String) : KLogger {
		private val logger: io.github.oshai.kotlinlogging.KLogger =
			io.github.oshai.kotlinlogging.KotlinLogging.logger(name)

		override var log: KLoggerMethod? = { level, t, message ->
			when (level) {
				KLogger.Level.TRACE -> logger.trace(t, message)
				KLogger.Level.DEBUG -> logger.debug(t, message)
				KLogger.Level.INFO -> logger.info(t, message)
				KLogger.Level.WARN -> logger.warn(t, message)
				KLogger.Level.ERROR -> logger.error(t, message)
			}
		}
	}

	override fun logger(logName: String): KLogger = Logger(logName)

}