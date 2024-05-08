package klog

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.lang.reflect.Modifier
import kotlin.reflect.KClass

private fun <T : Any> unwrapCompanionClass(clazz: Class<T>): Class<*> {
	return clazz.enclosingClass?.let { enclosingClass ->
		try {
			enclosingClass.declaredFields
				.find { field ->
					field.name == clazz.simpleName &&
							Modifier.isStatic(field.modifiers) &&
							field.type == clazz
				}
				?.run { enclosingClass }
		} catch (se: SecurityException) {
			// The security manager isn't properly set up, so it won't be possible
			// to search for the target declared field.
			null
		}
	} ?: clazz
}

actual fun <T : Any> loggerName(clazz: KClass<T>): String = unwrapCompanionClass(clazz.java).name

private class Slf4jLog(private val logger: Logger) : KLogger {

	override val name: String = logger.name

	override var log: KLoggerMethod? = { level, t, message ->
		when (level) {
			KLogger.Level.TRACE -> if (logger.isTraceEnabled) logger.trace(message(), t)
			KLogger.Level.DEBUG -> if (logger.isDebugEnabled) logger.debug(message(), t)
			KLogger.Level.INFO -> if (logger.isInfoEnabled) logger.info(message(), t)
			KLogger.Level.WARN -> if (logger.isWarnEnabled) logger.warn(message(), t)
			KLogger.Level.ERROR -> if (logger.isErrorEnabled) logger.error(message(), t)
		}
	}
}

object Slf4jLogging : KLogFactory() {
	override fun logger(logName: String): KLogger =
		Slf4jLog(LoggerFactory.getLogger(logName))
}

