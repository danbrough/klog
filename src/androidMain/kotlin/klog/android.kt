package klog

import android.util.Log

class AndroidLogger(override val name: String) : KLogger {

	override var log: KLoggerMethod? = { level, t, message ->
		val androidLevel = when (level) {
			KLogger.Level.TRACE -> Log.VERBOSE
			KLogger.Level.DEBUG -> Log.DEBUG
			KLogger.Level.INFO -> Log.INFO
			KLogger.Level.WARN -> Log.WARN
			KLogger.Level.ERROR -> Log.ERROR
		}

		if (Log.isLoggable(name, androidLevel)) {
			when (androidLevel) {
				Log.VERBOSE -> Log.v(name, message(), t)
				Log.DEBUG -> Log.d(name, message(), t)
				Log.INFO -> Log.i(name, message(), t)
				Log.WARN -> Log.w(name, message(), t)
				Log.ERROR -> Log.e(name, message(), t)
			}
		}
	}
}

object AndroidLogging : KLogFactory() {
	override fun logger(logName: String): KLogger = AndroidLogger(logName)
}

fun kloggingAndroid() {
	klogging = AndroidLogging
}

actual fun kloggingDefault(): KLogFactory = AndroidLogging