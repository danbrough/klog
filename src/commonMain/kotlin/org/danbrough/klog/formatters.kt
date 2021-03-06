package org.danbrough.klog

import kotlin.native.concurrent.ThreadLocal

typealias KMessageFormatter = (String, Level, String, Throwable?, StatementContext) -> String


val Level.color: Int
  get() = when (this) {
    Level.TRACE -> 35
    Level.DEBUG -> 36
    Level.INFO -> 32
    Level.WARN -> 33
    else -> 31
  }


@ThreadLocal
object KMessageFormatters {

  val simple: KMessageFormatter = { name, level, msg, exception, _ ->
    val l = level.toString().let { if (it.length < 5) " $it:" else "$it:" }
    "$l$name: $msg ${exception?.stackTraceToString()?.let { " :$it" } ?: ""}"
  }

  val verbose: KMessageFormatter = { name, level, msg, exception, ctx ->
    buildString {
      append("DUDE!:")
      append(level.toString().let { if (it.length < 5) " $it:" else "$it:" })
      append(name)
      append('\t')
      val threadID = ctx.threadID
      if (threadID.isNotEmpty())
        append("<$threadID>")


      ctx.line?.functionName?.also { append("${ctx.line.fileName}:${ctx.line.lineNumber}:${it}(): ") }
        ?: append(" ")

      append(msg)


      exception?.stackTraceToString()?.also { append(" :$it") }
    }
  }

  inline fun colored(crossinline formatter: KMessageFormatter): KMessageFormatter =
    { name, level, msg, exception, context ->
      "\u001b[0;${level.color}m${formatter.invoke(name, level, msg, exception, context)}\u001b[0m"
    }
}


val KMessageFormatter.colored: KMessageFormatter
  get() = KMessageFormatters.colored(this)



