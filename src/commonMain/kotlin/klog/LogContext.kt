package klog

data class LogContext(val tag: String, val level: Level, val colored: Boolean)

data class MutableLogContext(
  var tag: String = ROOT_TAG,
  var level: Level = Level.TRACE,
  var colored: Boolean = false,
)

typealias ConfigureContext = MutableLogContext.() -> Unit

internal val LogContext.config: MutableLogContext
  get() = MutableLogContext(tag, level, colored)

internal val MutableLogContext.context: LogContext
  get() = LogContext(tag, level, colored)


data class Thang(val id: String, val age: Int)



