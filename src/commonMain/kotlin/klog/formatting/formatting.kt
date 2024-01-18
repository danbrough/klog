package klog.formatting

import klog.Level

fun interface Formatter {
  fun format(level: Level, message: String, error: Throwable?): Message
}

data class Message(val logLevel: Level, var level: String, var message: String, var error: String?)

val simpleFormatter =
  Formatter { level, message, error -> Message(level, level.name, message, error?.message) }


val Formatter.colored: Formatter
  get() = Formatter { level, message, error ->
    this@colored.format(level, message, error).let {
      Message(level, it.level.colored(level), it.message.colored(level), it.error?.colored(level))
    }
  }

internal val Level.color: Int
  get() = when (this) {
    Level.TRACE -> 35
    Level.DEBUG -> 36
    Level.INFO -> 32
    Level.WARN -> 33
    else -> 31
  }

internal fun String.colored(level: Level) = "\u001b[0;${level.color}m$this\u001b[0m"
