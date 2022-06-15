@file:Suppress("unused")

package klog

import kotlin.reflect.KClass


val logFactory = createKogRegistry()


typealias LogWriter = (String) -> Unit

interface LogMessageContext {
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
) : LogMessageContext

expect fun platformLogMessageContext(): LogMessageContext

typealias LogMessageFunction = () -> String
typealias LogFormatter = (String, Level, String, Exception?, LogMessageContext) -> String

enum class Level {
  TRACE, DEBUG, INFO, WARN, ERROR, NONE;
}

data class KLog(
  val name: String,
  var level: Level,
  var formatter: LogFormatter,
  var writer: LogWriter?
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

  val isTraceEnabled: Boolean
    get() = level <= Level.TRACE

  val isDebugEnabled: Boolean
    get() = level <= Level.DEBUG

  val isInfoEnabled: Boolean
    get() = level <= Level.INFO

  val isWarnEnabled: Boolean
    get() = level <= Level.WARN


  @Suppress("MemberVisibilityCanBePrivate")
  val isErrorEnabled: Boolean
    get() = level <= Level.ERROR

  val isEnabled: Boolean
    get() = isErrorEnabled && writer != null


  private inline fun log(
    level: Level, msg: String?, err: Exception?, noinline msgProvider: LogMessageFunction?
  ) {
    writer ?: return
    if (msg == null && msgProvider == null) throw Error("Either provide a message or a message provider")
    if (level < this.level) return


    val message = "${msg ?: err?.message ?: ""} ${
      msgProvider?.invoke() ?: ""
    }".trim()

    val messageContext by lazy {
      platformLogMessageContext()
    }
    writer!!.invoke(formatter.invoke(name, level, message, err, messageContext))
  }

}



/*
@return Simple class name on JS and the fully qualified elsewhere
 */
expect fun KClass<*>.name(): String

inline fun <reified T : Any> T.klog(): KLog {
  return logFactory[this::class.name()]
}

inline fun klog(tag: String): KLog = logFactory[tag]



