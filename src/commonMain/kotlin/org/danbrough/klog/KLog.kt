package org.danbrough.klog

data class StatementContext(
  val threadName: String,
  val threadID: String = "",
  val line: LineContext? = null
) {
  val time: Long
    get() = getTimeMillis()

  data class LineContext(
    val lineNumber: Int = -1,
    val functionName: String? = null,
    val className: String? = null,
    val fileName: String? = null,
  )
}

expect fun platformStatementContext(): StatementContext

//for formatting the [KLog.tag] field
typealias KTagFormatter = (String) -> String


abstract class KLog(val tag: String) {

  data class Conf(
    var level: Level,
    var writer: KLogWriter,
    var messageFormatter: KMessageFormatter
  )


  companion object {
    const val ROOT_LOG_TAG = ""
  }

  abstract val conf: Conf

  inline fun trace(
    msg: String? = null,
    err: Throwable? = null,
    noinline msgProvider: LogMessageFunction? = null
  ) = log(Level.TRACE, msg, err, msgProvider)

  inline fun debug(
    msg: String? = null,
    err: Throwable? = null,
    noinline msgProvider: LogMessageFunction? = null
  ) = log(Level.DEBUG, msg, err, msgProvider)

  inline fun info(
    msg: String? = null,
    err: Throwable? = null,
    noinline msgProvider: LogMessageFunction? = null
  ) = log(Level.INFO, msg, err, msgProvider)

  inline fun warn(
    msg: String? = null,
    err: Throwable? = null,
    noinline msgProvider: LogMessageFunction? = null
  ) = log(Level.WARN, msg, err, msgProvider)

  inline fun error(
    msg: String? = null,
    err: Throwable? = null,
    noinline msgProvider: LogMessageFunction? = null
  ) = log(Level.ERROR, msg, err, msgProvider)

  val isTraceEnabled: Boolean
    inline get() = conf.level <= Level.TRACE

  val isDebugEnabled: Boolean
    inline get() = conf.level <= Level.DEBUG

  val isInfoEnabled: Boolean
    inline get() = conf.level <= Level.INFO

  val isWarnEnabled: Boolean
    inline get() = conf.level <= Level.WARN

  val isErrorEnabled: Boolean
    inline get() = conf.level <= Level.ERROR

  val isEnabled: Boolean
    get() = isErrorEnabled && conf.writer != KLogWriters.noop


  abstract fun log(
    level: Level,
    msg: String?,
    err: Throwable?,
    msgProvider: LogMessageFunction?
  )
}

@Suppress("MemberVisibilityCanBePrivate", "OVERRIDE_BY_INLINE")
class KLogImpl(tag: String, override val conf: KLog.Conf) : KLog(tag) {

  private val displayName: String = tag


  @Suppress("NOTING_TO_INLINE")
  override fun log(
    level: Level,
    msg: String?,
    err: Throwable?,
    msgProvider: LogMessageFunction?
  ) {
    val logWriter = conf.writer
    if (logWriter == KLogWriters.noop) return
    if (msg == null && msgProvider == null) throw Error("Either provide a message or a message provider")
    if (level < conf.level) return


    val message = "${msg ?: err?.message ?: ""} ${
      msgProvider?.invoke() ?: ""
    }".trim()

    val ctx by lazy {
      platformStatementContext()
    }

    logWriter(
      displayName,
      level,
      conf.messageFormatter(tag, level, message, err, ctx),
      err
    )
  }


}



