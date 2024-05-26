package klog

import klog.stdout.StdoutLogging
import klog.stdout.colorString
import kotlin.reflect.KClass

object JsLogFactory : KLogFactory() {

	init {
		StdoutLogging.coloredOutput = false
	}

	private var log: LoggerMethod = { level, name, message, t ->
		console.log(StdoutLogging.formatter.invoke(level, name, message))
		if (t != null) console.log(colorString(Logger.Level.ERROR, t.stackTraceToString()))
	}

	override fun logger(logName: String) = LoggerImpl(logName, log)
}

internal actual fun kloggingDefault(): KLogFactory = JsLogFactory

actual fun <T : Any> loggerName(clazz: KClass<T>): String = "KOTLIN"