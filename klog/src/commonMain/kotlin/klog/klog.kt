package klog

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

/*interface KLog {
  val name: String
  val level: Level
  val formatter: LogFormatter
  val writer: LogWriter?

  fun trace(msg: String? = null, err: Exception? = null, msgProvider: LogMessageFunction? = null)
  fun debug(msg: String? = null, err: Exception? = null, msgProvider: LogMessageFunction? = null)
  fun info(msg: String? = null, err: Exception? = null, msgProvider: LogMessageFunction? = null)
  fun warn(msg: String? = null, err: Exception? = null, msgProvider: LogMessageFunction? = null)
  fun error(msg: String? = null, err: Exception? = null, msgProvider: LogMessageFunction? = null)

}*/

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


interface KLogRegistry {
  var rootLog: KLog
  operator fun get(name: String): KLog
  operator fun set(name: String, log: KLog)
  fun reset()
}

open class DefaultLogRegistry : KLogRegistry {
  override var rootLog: KLog = KLog("", Level.NONE, LogFormatters.simple, LogWriters.stdOut)
    set(value) {
      field = value
      println("SETTING ROOT LOG!!!")
      this[""] = value
    }

  private var logs = mutableMapOf<String, KLog?>()

  override operator fun get(name: String): KLog {
    if (name.isEmpty()) return rootLog
    val log = logs[name]
    if (log != null) return log
    val parentName = name.substringBeforeLast('.', "")
    return get(parentName).copy(name = name).also {
      logs[name] = it
    }
  }

  override operator fun set(name: String, log: KLog) {
    logs = logs.filterKeys { it.startsWith(name) }.toMutableMap()
    logs[name] = log
  }

  override fun reset() {
    logs.clear()
  }
}

val logFactory = klogRegistry()


expect fun klogRegistry(): KLogRegistry

@Suppress("NOTHING_TO_INLINE")
inline fun <reified T : Any> T.klog(): KLog = logFactory[T::class.qualifiedName!!]
inline fun klog(tag: String): KLog = logFactory[tag]



