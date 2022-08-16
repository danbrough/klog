package klog

import kotlin.native.concurrent.ThreadLocal

typealias KMessageFormatter = (String, Level, String, Throwable?, StatementContext) -> String

//for formatting the [KLog.displayTag] field
typealias KDisplayTagFormatter = (tag: String, width: Int) -> String

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

  val simple: KMessageFormatter = { tag, level, msg, exception, _ ->
    val l = level.toString().let { if (it.length < 5) " $it:" else "$it:" }
    "$l$tag: $msg ${exception?.stackTraceToString()?.let { " :$it" } ?: ""}"
  }

  val verbose: KMessageFormatter = { tag, level, msg, exception, ctx ->
    buildString {
      append(level.toString().let { if (it.length < 5) " $it:" else "$it:" })
      append(tag)
      append(':')
      val threadID = ctx.threadID
      if (threadID.isNotEmpty())
        append("<$threadID>:")


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


private fun defaultDisplayTagFormatter(t: String, length: Int): String {
  var tag = t.trim()
  if (tag.length <= length)
    //return tag.padEnd(length, '-')
    return tag
  tag = tag.filter { !it.isWhitespace() }
  if (tag.length <= length)
    return tag
    //return tag.padEnd(length, '-')
  val c = length / 2
  return tag.substring(0 until c) + tag.substring(tag.length - c until tag.length)
}

val DefaultDisplayTagFormatter: KDisplayTagFormatter = ::defaultDisplayTagFormatter

