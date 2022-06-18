package org.danbrough.klog

import kotlin.native.concurrent.ThreadLocal

typealias KLogFormatter = (String, Level, String, Throwable?, StatementContext) -> String


val Level.color: Int
  get() = when (this) {
    Level.TRACE -> 35
    Level.DEBUG -> 36
    Level.INFO -> 32
    Level.WARN -> 33
    else -> 31
  }


@ThreadLocal
object KLogFormatters {

  val simple: KLogFormatter = { name, level, msg, exception, _ ->
    val l = level.toString().let { if (it.length < 5) " $it:" else "$it:" }
    "$l$name: $msg ${exception?.stackTraceToString()?.let { " :$it" } ?: ""}"
  }

  val verbose: KLogFormatter = { name, level, msg, exception, ctx ->
    val l = level.toString().let { if (it.length < 5) " $it:" else "$it:" }
    val lineInfo = ctx.line?.functionName?.let { "${it}():${ctx.line.lineNumber} " } ?: ""
    "$l$name ${ctx.line?.fileName?.let { "$it:" } ?: ""}${lineInfo}$msg ${
      exception?.stackTraceToString()?.let { " :$it" } ?: ""
    }"
  }

  inline fun colored(crossinline formatter: KLogFormatter): KLogFormatter =
    { name, level, msg, exception, context ->
      "\u001b[0;${level.color}m${formatter.invoke(name, level, msg, exception, context)}\u001b[0m"
    }
}


val KLogFormatter.colored: KLogFormatter
  get() = KLogFormatters.colored(this)



