package klog


typealias LogWriter = (String) -> Unit

data class LogEntryContext(
  val threadName: String,
  val threadID: Long,
  val lineNumber: Int = -1,
  val functionName: String? = null,
  val className: String? = null
)

typealias LogMessageFunction = (() -> LogEntryContext) -> String


enum class Level {
  TRACE, DEBUG, INFO, WARN, ERROR, NONE;
}

data class KLog(
  val level: Level,
  val formatter: LogFormatter,
  val writer: LogWriter? = null
) {

  fun trace(msg: String? = null, err: Exception? = null, msgProvider: LogMessageFunction? = null) =
    log(Level.TRACE, msg, err, msgProvider)

  fun debug(msg: String? = null, err: Exception? = null, msgProvider: LogMessageFunction? = null) =
    log(Level.DEBUG, msg, err, msgProvider)

  fun info(msg: String? = null, err: Exception? = null, msgProvider: LogMessageFunction? = null) =
    log(Level.INFO, msg, err, msgProvider)

  fun warn(msg: String? = null, err: Exception? = null, msgProvider: LogMessageFunction? = null) =
    log(Level.WARN, msg, err, msgProvider)

  fun error(msg: String? = null, err: Exception? = null, msgProvider: LogMessageFunction? = null) =
    log(Level.ERROR, msg, err, msgProvider)

  private inline fun log(
    level: Level,
    msg: String?,
    err: Exception?,
    noinline msgProvider: LogMessageFunction?
  ) {
    writer ?: return
    if (msg == null && msgProvider == null) throw Error("Either provide a message or a message provider")
    if (level < this.level) return

    val message = "${msg ?: err?.message ?: ""} ${
      msgProvider?.invoke(
        logFactory()::logEntryContext
      ) ?: ""
    }".trim()
    writer.invoke(formatter.invoke(level, message, err))
  }
}

interface KLogFactory {
  var rootLogger: KLog
  fun <T> getLog(t: T): KLog
  fun logEntryContext(): LogEntryContext
}

expect fun logFactory(): KLogFactory


abstract class BaseLogFactory : KLogFactory {
  override var rootLogger: KLog = KLog(Level.NONE, LogFormatters.simple, null)

  override fun <T> getLog(t: T): KLog = rootLogger

}

@Suppress("NOTHING_TO_INLINE")
inline fun <T : Any> T.klog(): KLog = logFactory().getLog(this::class)



