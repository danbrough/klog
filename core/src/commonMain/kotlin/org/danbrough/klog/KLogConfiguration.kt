package org.danbrough.klog


open class KLogConfiguration(open val logWriters: List<KLogWriter> = emptyList()) {
  var coloredOutput: Boolean = true

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

