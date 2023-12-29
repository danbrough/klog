package klog

interface KLog {
  val logger: KLogger
  fun log(level: Level, message: String, error: Throwable? = null)
}

class KLogImpl(override val logger: KLogger) : KLog {
  override fun log(level: Level, message: String, error: Throwable?) {
    if (level < logger.level) return

    println("path<${logger.name}>:$level: $message err:$error")
  }

  override fun toString() = "KLog[$logger]"
}