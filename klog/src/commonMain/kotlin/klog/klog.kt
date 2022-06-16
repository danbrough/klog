@file:Suppress("unused")

package klog

import kotlin.reflect.KClass


val kLogRegistry = createKLogRegistry()


typealias LogWriter = (String) -> Unit

interface LogMessageContext {
  val time: Long
  val threadName: String
  val threadID: Long
  val lineNumber: Int
  val functionName: String?
  val className: String?
}



data class LogMessageContextImpl(
  override val threadName: String,
  override val threadID: Long,
  override val lineNumber: Int = -1,
  override val functionName: String? = null,
  override val className: String? = null
) : LogMessageContext {
  override val time: Long
    get() = getTimeMillis()
}

expect fun getTimeMillis(): Long

expect fun platformLogMessageContext(): LogMessageContext

typealias LogMessageFunction = () -> String
typealias LogFormatter = (String, Level, String, Throwable?, LogMessageContext) -> String

enum class Level {
  ALL, TRACE, DEBUG, INFO, WARN, ERROR, NONE;
}

@Suppress("MemberVisibilityCanBePrivate")
data class KLog(
  private val registry: KLogRegistry,
  val name: String,
  private var _level: Level,
  private var _formatter: LogFormatter,
  private var _writer: LogWriter?
) {

  var level: Level
    get() = _level
    set(value) {
      registry.applyToBranch(name) {
        _level = value
      }
    }

  var formatter: LogFormatter
    get() = _formatter
    set(value) {
      registry.applyToBranch(name) {
        _formatter = value
      }
    }

  var writer: LogWriter?
    get() = _writer
    set(value) {
      registry.applyToBranch(name) {
        _writer = value
      }
    }

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
    get() = isErrorEnabled && writer != null


  private inline fun log(
    level: Level, msg: String?, err: Throwable?, noinline msgProvider: LogMessageFunction?
  ) {
    val logWriter = writer ?: return
    if (msg == null && msgProvider == null) throw Error("Either provide a message or a message provider")
    if (level < this.level) return


    val message = "${msg ?: err?.message ?: ""} ${
      msgProvider?.invoke() ?: ""
    }".trim()

    val messageContext by lazy {
      platformLogMessageContext()
    }
    logWriter.invoke(formatter.invoke(name, level, message, err, messageContext))
  }

}


/*
@return Simple class name on JS and the fully qualified elsewhere
 */
expect fun KClass<*>.name(): String

inline fun <reified T : Any> T.klog(): KLog {
  return kLogRegistry[this::class.name()]
}

inline fun klog(tag: String): KLog = kLogRegistry[tag]



