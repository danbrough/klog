package klog

typealias KLoggerMethod = (level: KLogger.Level, t: Throwable?, message: () -> String) -> Unit

interface KLogger {

	val name:String

	enum class Level {
		TRACE, DEBUG, INFO, WARN, ERROR;
	}

	var log: KLoggerMethod?

	fun trace(t: Throwable? = null, message: () -> String) = log?.invoke(Level.TRACE, t, message)
	fun debug(t: Throwable? = null, message: () -> String) = log?.invoke(Level.DEBUG, t, message)
	fun info(t: Throwable? = null, message: () -> String) = log?.invoke(Level.INFO, t, message)
	fun warn(t: Throwable? = null, message: () -> String) = log?.invoke(Level.WARN, t, message)
	fun error(t: Throwable? = null, message: () -> String) = log?.invoke(Level.ERROR, t, message)

}