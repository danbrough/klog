@file:Suppress("NOTHING_TO_INLINE", "KotlinRedundantDiagnosticSuppress")

package klog

enum class Level {
  TRACE, DEBUG, INFO, WARN, ERROR;
}

data class KLogContext(val level: Level,val colored:Boolean)

interface KLog {
  val log: (Level, String, Throwable?) -> Unit
}

val rootContext = KLogContext(Level.TRACE,true)

fun toKLogTag(name:String):String = name

inline fun KLog.trace(msg: String, err: Throwable? = null) = log(Level.TRACE, msg, err)
inline fun KLog.debug(msg: String, err: Throwable? = null) = log(Level.DEBUG, msg, err)
inline fun KLog.info(msg: String, err: Throwable? = null) = log(Level.INFO, msg, err)
inline fun KLog.warn(msg: String, err: Throwable? = null) = log(Level.WARN, msg, err)
inline fun KLog.error(msg: String, err: Throwable? = null) = log(Level.ERROR, msg, err)

expect inline fun <reified T : Any> T.klog(noinline config: (KLogContext.() -> Unit)? = null): KLog


