package klog

abstract class KLogFactory {
	abstract fun logger(logName: String): KLogger
}


object StdoutLogging : KLogFactory() {
	class Logger(override val name: String) : KLogger {
		override var log: KLoggerMethod? = { level, t, message ->
			println("$level:$name: ${message()}")
			if (t != null) println(t.stackTraceToString())
		}
	}

	override fun logger(logName: String): KLogger = Logger(logName)
}

object NOOPLogging : KLogFactory(), KLogger {
	override fun logger(logName: String): KLogger = this

	override val name: String = ""

	override var log: KLoggerMethod? = null
}



