package org.danbrough.klog

data class StatementContext(
  val threadName: String,
  val threadID: Long,
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

//for formatting the [KLog.name] field
typealias KNameFormatter = (String) -> String


interface KLog {
  val name: String
  var level: Level
  var writer: KLogWriter
  var messageFormatter: KMessageFormatter
  var nameFormatter: KNameFormatter?

  val displayName: String
    get() = nameFormatter?.invoke(name) ?: name

  fun copy(
    name: String = this.name,
    level: Level = this.level,
    writer: KLogWriter = this.writer,
    messageFormatter: KMessageFormatter = this.messageFormatter,
    nameFormatter: KNameFormatter? = this.nameFormatter
  ): KLog


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
    get() = level <= Level.TRACE

  val isDebugEnabled: Boolean
    get() = level <= Level.DEBUG

  val isInfoEnabled: Boolean
    get() = level <= Level.INFO

  val isWarnEnabled: Boolean
    get() = level <= Level.WARN

  val isErrorEnabled: Boolean
    get() = level <= Level.ERROR

  val isEnabled: Boolean
    get() = isErrorEnabled && writer != KLogWriters.noop
}

@Suppress("MemberVisibilityCanBePrivate")
data class KLogImpl(
  private val registry: KLogRegistry,
  override val name: String,
  private var _level: Level,
  private var _writer: KLogWriter,
  private var _messageFormatter: KMessageFormatter,
  private var _nameFormatter: KNameFormatter? = null
) : KLog {

  override var level: Level
    get() = _level
    set(value) {
      registry.applyToBranch(name) {
        (this as KLogImpl)._level = value
      }
    }

  override var writer: KLogWriter
    get() = _writer
    set(value) {
      registry.applyToBranch(name) {
        (this as KLogImpl)._writer = value
      }
    }

  override var messageFormatter: KMessageFormatter
    get() = _messageFormatter
    set(value) {
      registry.applyToBranch(name) {
        (this as KLogImpl)._messageFormatter = value
      }
    }

  override var nameFormatter: KNameFormatter?
    get() = _nameFormatter
    set(value) {
      registry.applyToBranch(name) {
        (this as KLogImpl)._nameFormatter = value
      }
    }

  override fun copy(
    name: String,
    level: Level,
    writer: KLogWriter,
    messageFormatter: KMessageFormatter,
    nameFormatter: KNameFormatter?
  ): KLog = KLogImpl(registry, name, level, writer, messageFormatter, nameFormatter)

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


  private inline fun log(
    level: Level, msg: String?, err: Throwable?, noinline msgProvider: LogMessageFunction?
  ) {
    val logWriter = writer
    if (logWriter == KLogWriters.noop) return
    if (msg == null && msgProvider == null) throw Error("Either provide a message or a message provider")
    if (level < this.level) return


    val message = "${msg ?: err?.message ?: ""} ${
      msgProvider?.invoke() ?: ""
    }".trim()

    val ctx by lazy {
      platformStatementContext()
    }

    logWriter.invoke(
      displayName,
      level,
      messageFormatter.invoke(name, level, message, err, ctx),
      err
    )
  }


}



