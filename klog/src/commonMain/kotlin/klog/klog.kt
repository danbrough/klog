package klog

import kotlin.reflect.KClass


typealias LogWriter = (String) -> Unit

data class LogEntryContext(
  val threadName: String,
  val threadID: Long,
  val lineNumber: Int = -1,
  val functionName: String? = null,
  val className: String? = null
)

expect fun logEntryContext(): LogEntryContext

typealias LogMessageFunction = (() -> LogEntryContext) -> String


enum class Level {
  TRACE, DEBUG, INFO, WARN, ERROR, NONE;
}


abstract class KLog(
  val source: Any? = null,
  val level: Level,
  val formatter: LogFormatter,
  val writer: LogWriter?
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

  @Suppress("WeakerAccess")
  protected inline fun log(
    level: Level,
    msg: String?,
    err: Exception?,
    noinline msgProvider: LogMessageFunction?
  ) {
    writer ?: return
    if (msg == null && msgProvider == null) throw Error("Either provide a message or a message provider")
    if (level < this.level) return

    val message = "${msg ?: err?.message ?: ""} ${
      msgProvider?.invoke(::logEntryContext) ?: ""
    }".trim()
    writer.invoke(formatter.invoke(level, message, err))
  }
}

class KLogImpl(
  level: Level,
  formatter: LogFormatter,
  writer: LogWriter,
  source: Any? = null,
) : KLog(source, level, formatter, writer)


open class KLogFactory{
  open var rootLog: KLog = KLogImpl(Level.NONE,LogFormatters.simple,LogWriters.stdOut,null)
  open fun <T:Any> getLog(clazz:KClass<T>):KLog = rootLog
}

expect fun klogFactory(): KLogFactory

@Suppress("NOTHING_TO_INLINE")
inline fun <T : Any> T.klog(): KLog = klogFactory().getLog(this::class)



