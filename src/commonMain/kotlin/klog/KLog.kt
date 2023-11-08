package klog

interface KLog {
  val config: LogConfig
  fun log(level: Level, message: String, error: Throwable? = null)
}

class KLogImpl(override val config: LogConfig) : KLog {
  override fun log(level: Level, message: String, error: Throwable?) {
    println("${config.tag}:$level: $message err:$error")
  }
}