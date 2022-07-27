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

data class KLogConf(
  var level: Level,
  var writer: KLogWriter,
  var messageFormatter: KMessageFormatter
)

interface KLog {
  val tag: String

  fun trace(msg: String? = null, err: Throwable? = null) = trace(msg, err, null)

  fun trace(msg: String? = null, err: Throwable? = null, msgProvider: LogMessageFunction? = null)

  fun debug(msg: String? = null, err: Throwable? = null) = debug(msg, err, null)

  fun debug(msg: String? = null, err: Throwable? = null, msgProvider: LogMessageFunction? = null)

  fun info(msg: String? = null, err: Throwable? = null) = info(msg, err, null)

  fun info(msg: String? = null, err: Throwable? = null, msgProvider: LogMessageFunction? = null)

  fun warn(msg: String? = null, err: Throwable? = null) = warn(msg, err, null)

  fun warn(msg: String? = null, err: Throwable? = null, msgProvider: LogMessageFunction? = null)

  fun error(msg: String? = null, err: Throwable? = null) = error(msg, err, null)

  fun error(msg: String? = null, err: Throwable? = null, msgProvider: LogMessageFunction? = null)

  val isTraceEnabled: Boolean

  val isDebugEnabled: Boolean

  val isInfoEnabled: Boolean

  val isWarnEnabled: Boolean

  val isErrorEnabled: Boolean

  val isEnabled: Boolean
}

@Suppress("MemberVisibilityCanBePrivate", "OVERRIDE_BY_INLINE")
data class KLogImpl(
  private val registry: KLogFactory,
  override val tag: String,
  var conf: KLogConf
) : KLog {

  private val displayName: String = tag

  override val isTraceEnabled: Boolean
    inline get() = conf.level >= Level.TRACE

  override val isDebugEnabled: Boolean
    inline get() = conf.level >= Level.DEBUG

  override val isInfoEnabled: Boolean
    inline get() = conf.level >= Level.INFO

  override val isWarnEnabled: Boolean
    inline get() = conf.level >= Level.WARN

  override val isErrorEnabled: Boolean
    inline get() = conf.level >= Level.ERROR


  override val isEnabled: Boolean
    get() = isErrorEnabled


  override fun trace(msg: String?, err: Throwable?, msgProvider: LogMessageFunction?) =
    log(Level.TRACE, msg, err, msgProvider)

  override fun debug(msg: String?, err: Throwable?, msgProvider: LogMessageFunction?) =
    log(Level.DEBUG, msg, err, msgProvider)

  override fun info(msg: String?, err: Throwable?, msgProvider: LogMessageFunction?) =
    log(Level.INFO, msg, err, msgProvider)

  override fun warn(msg: String?, err: Throwable?, msgProvider: LogMessageFunction?) =
    log(Level.WARN, msg, err, msgProvider)

  override fun error(msg: String?, err: Throwable?, msgProvider: LogMessageFunction?) =
    log(Level.ERROR, msg, err, msgProvider)


  @Suppress("NOTING_TO_INLINE")
  private inline fun log(
    level: Level,
    msg: String?,
    err: Throwable?,
    noinline msgProvider: LogMessageFunction?
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

    logWriter.invoke(
      displayName,
      level,
      conf.messageFormatter(tag, level, message, err, ctx),
      err
    )
  }


}



