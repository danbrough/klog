package org.danbrough.klog

val StdLogWriters = listOf(object : KLogWriter {
  override fun writeLog(
    conf: KLogConfiguration, level: Level, name: String, message: String, t: Throwable?
  ) {
    println("$level $name $message conf: $conf")
  }
})


open class KLogConfiguration(open val logWriters: List<KLogWriter> = StdLogWriters) {
  inline fun write(
    level: Level, name: String, message: MessageBlock, t: Throwable? = null
  ) {
    if (logWriters.isNotEmpty()) {
      val message: String = message().toString()
      logWriters.forEach {
        it.writeLog(this, level, name, message, t)
      }
    }
  }

}

object DefaultKLogConfiguration : KLogConfiguration()