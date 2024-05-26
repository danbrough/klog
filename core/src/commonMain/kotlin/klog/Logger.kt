package klog

import klog.Logger.Level

typealias LoggerMethod = (level: Level, name: String, message: () -> String?, t: Throwable?) -> Unit

interface Logger {

	val name: String

	enum class Level {
		TRACE, DEBUG, INFO, WARN, ERROR, NONE;
	}

	fun trace(t: Throwable? = null, message: () -> String?)
	fun debug(t: Throwable? = null, message: () -> String?)
	fun info(t: Throwable? = null, message: () -> String?)
	fun warn(t: Throwable? = null, message: () -> String?)
	fun error(t: Throwable? = null, message: () -> String?)
}

interface DelegatingLogger : Logger {

	var log: LoggerMethod

	override fun trace(t: Throwable?, message: () -> String?) =
		log.invoke(Level.TRACE, name, message, t)

	override fun debug(t: Throwable?, message: () -> String?) =
		log.invoke(Level.DEBUG, name, message, t)

	override fun info(t: Throwable?, message: () -> String?) =
		log.invoke(Level.INFO, name, message, t)

	override fun warn(t: Throwable?, message: () -> String?) =
		log.invoke(Level.WARN, name, message, t)

	override fun error(t: Throwable?, message: () -> String?) =
		log.invoke(Level.ERROR, name, message, t)
}


class LoggerImpl(override val name: String, override var log: LoggerMethod) : DelegatingLogger

