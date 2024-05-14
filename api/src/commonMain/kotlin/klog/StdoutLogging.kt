@file:Suppress("MemberVisibilityCanBePrivate")

package klog


typealias StdoutMessageFormatter = (level: Logger.Level, name: String, message: () -> String) -> String


object StdoutMessageFormatters {
  val defaultFormatter: StdoutMessageFormatter = { level, name, message ->
    "$level:$name: ${message()}"
  }

  fun coloredFormatter(formatter: StdoutMessageFormatter): StdoutMessageFormatter =
    { level, name, message ->
      formatter(level, name, message)
    }
}

object StdoutLogging : KLogFactory() {

  var formatter: StdoutMessageFormatter = StdoutMessageFormatters.defaultFormatter

  var log: LoggerMethod = { level, name, message, t ->
    println(formatter.invoke(level, name, message))
    if (t != null) println(t.stackTraceToString())
  }

  operator fun invoke(block: StdoutLogging.() -> Unit): StdoutLogging {
    block.invoke(this)
    return this
  }

  override fun logger(logName: String) = LoggerImpl(logName, log)
}