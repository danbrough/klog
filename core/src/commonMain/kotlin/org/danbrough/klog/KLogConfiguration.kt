package org.danbrough.klog

import org.danbrough.klog.std.colored

fun KLogConfiguration.colorString(level: Level, message: String): String =
  if (coloredOutput) level.colored(message) else message


open class KLogConfiguration(vararg writers: KLogWriter) {

  open val logWriters: List<KLogWriter> = writers.toList()

  var coloredOutput: Boolean = Env.klogColored

  var formatMessage: (level:Level,name:String,message:String,e:Throwable?)->String =
    { level, name, message, e ->
      "${level.name.padStart(5,' ')}:$name: $message ${e?.stackTraceToString() ?: ""}"
    }

  inline fun write(
    level: Level, name: String, message: MessageBlock, t: Throwable? = null
  ) {
    if (logWriters.isNotEmpty()) {
      val message: String = message().toString()
      logWriters.forEach {
        it(this, level, name, message, t)
      }
    }
  }
}

