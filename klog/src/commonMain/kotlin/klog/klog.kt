package klog

import kotlin.native.concurrent.ThreadLocal


typealias LogMessageFunction = (Exception?) -> String
typealias LogFormatter = (Level, String, Exception?) -> String
typealias LogWriter = (String) -> Unit


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

    val message = "${msg ?: err?.message ?: ""} ${msgProvider?.invoke(err) ?: ""}".trim()
    writer.invoke(formatter.invoke(level, message, err))
  }


}


@ThreadLocal
object KLogFactory {
  var rootLogger: KLog = KLog(Level.NONE, LogFormatters.simple, null)

  init {
    initLogging()
  }



  fun <T> getLog(t: T): KLog = rootLogger

}


inline fun <T : Any> T.klog(): KLog = KLogFactory.getLog(this::class)

expect fun initLogging()

