package klog

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

class KLog(private val tag: String, val conf: Conf) {

  private var displayTag: String? = null

  data class Conf(
    var level: Level,
    var writer: KLogWriter,
    var messageFormatter: KMessageFormatter,
    val displayTagLength: Int = 12,
    //set to null to disable display tag formatting
    val displayTagFormatter: KDisplayTagFormatter? = DefaultDisplayTagFormatter
  )

  companion object {
    const val ROOT_LOG_TAG = ""
  }

  inline fun trace(
    msg: String? = null,
    err: Throwable? = null
  ) = log(Level.TRACE, msg, err, null)

  inline fun trace(
    msg: String? = null,
    err: Throwable? = null,
    noinline msgProvider: LogMessageFunction
  ) = log(Level.TRACE, msg, err, msgProvider)

  inline fun debug(
    msg: String? = null,
    err: Throwable? = null
  ) = log(Level.DEBUG, msg, err, null)

  inline fun debug(
    msg: String? = null,
    err: Throwable? = null,
    noinline msgProvider: LogMessageFunction
  ) = log(Level.DEBUG, msg, err, msgProvider)

  inline fun info(
    msg: String? = null,
    err: Throwable? = null
  ) = log(Level.INFO, msg, err, null)

  inline fun info(
    msg: String? = null,
    err: Throwable? = null,
    noinline msgProvider: LogMessageFunction
  ) = log(Level.INFO, msg, err, msgProvider)

  inline fun warn(
    msg: String? = null,
    err: Throwable? = null
  ) = log(Level.WARN, msg, err, null)

  inline fun warn(
    msg: String? = null,
    err: Throwable? = null,
    noinline msgProvider: LogMessageFunction
  ) = log(Level.WARN, msg, err, msgProvider)

  inline fun error(
    msg: String? = null,
    err: Throwable? = null
  ) = log(Level.ERROR, msg, err, null)

  inline fun error(
    msg: String? = null,
    err: Throwable? = null,
    noinline msgProvider: LogMessageFunction
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


  //@Suppress("NOTING_TO_INLINE")
  fun log(
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

    val formattedTag =
      displayTag ?: (conf.displayTagFormatter?.invoke(tag, conf.displayTagLength) ?: tag).also {
        displayTag = it
      }

    logWriter(
      formattedTag,
      level,
      conf.messageFormatter(tag, level, message, err, ctx),
      err
    )
  }
}


