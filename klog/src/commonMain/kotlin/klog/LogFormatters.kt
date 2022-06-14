package klog

import kotlin.native.concurrent.ThreadLocal


val Level.color: Int
  get() = when (this) {
    Level.TRACE -> 35
    Level.DEBUG -> 36
    Level.INFO -> 32
    Level.WARN -> 33
    else -> 31
  }


@ThreadLocal
object LogFormatters {
  var provideLineNumber: () -> Int = { -1 }


  val simple: LogFormatter = { level, msg, exception->
    val l = level.toString().let { if (it.length < 5) " $it:" else "$it:" }
    "$l $msg ${exception?.stackTraceToString()?.let { " :$it" } ?: ""}"
  }

  val verbose: LogFormatter = { level, msg, exception->
    val l = level.toString().let { if (it.length < 5) " $it:" else "$it:" }
    "$l $msg ${exception?.stackTraceToString()?.let { " :$it" } ?: ""}"
  }

  inline fun colored(crossinline formatter: LogFormatter): LogFormatter =
    { level, msg, exception ->
      "\u001b[0;${level.color}m${formatter.invoke(level, msg, exception)}\u001b[0m"
    }
}



