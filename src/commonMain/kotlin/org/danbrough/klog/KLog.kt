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


@Suppress("MemberVisibilityCanBePrivate")
data class KLog(
  private val registry: KLogRegistry,
  val name: String,
  private var _level: Level,
  private var _formatter: KLogFormatter,
  private var _writer: KLogWriter
) {

  var level: Level
    get() = _level
    set(value) {
      _level = value
      registry.applyToBranch(name) {
        _level = value
      }
    }

  var formatter: KLogFormatter
    get() = _formatter
    set(value) {
      _formatter = value
      registry.applyToBranch(name) {
        _formatter = value
      }
    }

  var writer: KLogWriter
    get() = _writer
    set(value) {
      _writer = value
      registry.applyToBranch(name) {
        _writer = value
      }
    }

/*
  fun copyOf(
    level: Level = _level,
    formatter: KLogFormatter = _formatter,
    writer: KLogWriter = _writer
  ) = this.copy(_level = level, _writer = writer, _formatter = formatter)
*/

  fun trace(msg: String? = null, err: Throwable? = null, msgProvider: LogMessageFunction? = null) =
    log(Level.TRACE, msg, err, msgProvider)

  fun debug(msg: String? = null, err: Throwable? = null, msgProvider: LogMessageFunction? = null) =
    log(Level.DEBUG, msg, err, msgProvider)

  fun info(msg: String? = null, err: Throwable? = null, msgProvider: LogMessageFunction? = null) =
    log(Level.INFO, msg, err, msgProvider)

  fun warn(msg: String? = null, err: Throwable? = null, msgProvider: LogMessageFunction? = null) =
    log(Level.WARN, msg, err, msgProvider)

  fun error(msg: String? = null, err: Throwable? = null, msgProvider: LogMessageFunction? = null) =
    log(Level.ERROR, msg, err, msgProvider)

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


  @Suppress("NOTING_TO_INLINE")
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
    logWriter.invoke(name, level, formatter.invoke(name, level, message, err, ctx), err)
  }

}



