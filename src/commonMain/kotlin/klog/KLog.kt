package klog

interface KLog {
  val context:LogContext
  fun log(level: Level, message: String, err: Throwable?)
}

class DefaultLog(override val context: LogContext) : KLog {
  override fun log(level: Level, message: String, err: Throwable?) {
    if (context.level > level) return@log


    println("${context.tag}:$message err:$err")
  }

}