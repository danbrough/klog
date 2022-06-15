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

  val simple: LogFormatter = { name,level, msg, exception, _ ->
    val l = level.toString().let { if (it.length < 5) " $it:" else "$it:" }
    "$l$name: $msg ${exception?.stackTraceToString()?.let { " :$it" } ?: ""}"
  }

  val verbose: LogFormatter = {name, level, msg, exception, ctx ->
    val l = level.toString().let { if (it.length < 5) " $it:" else "$it:" }
    "$l$name<${ctx.className}:${ctx.functionName}:${ctx.lineNumber}> $msg ${
      exception?.stackTraceToString()?.let { " :$it" } ?: ""
    }"
  }

  inline fun colored(crossinline formatter: LogFormatter): LogFormatter =
    { name,level, msg, exception, context ->
      "\u001b[0;${level.color}m${formatter.invoke(name,level, msg, exception, context)}\u001b[0m"
    }
}



