@file:Suppress("NOTHING_TO_INLINE", "KotlinRedundantDiagnosticSuppress")

package klog

enum class Level {
  TRACE, DEBUG, INFO, WARN, ERROR;
}

const val ROOT_PATH = ""


inline fun KLog.trace(msg: String, err: Throwable? = null) = log(Level.TRACE, msg, err)
inline fun KLog.debug(msg: String, err: Throwable? = null) = log(Level.DEBUG, msg, err)
inline fun KLog.info(msg: String, err: Throwable? = null) = log(Level.INFO, msg, err)
inline fun KLog.warn(msg: String, err: Throwable? = null) = log(Level.WARN, msg, err)
inline fun KLog.error(msg: String, err: Throwable? = null) = log(Level.ERROR, msg, err)

@KLogDSL
inline fun <reified T : Any> T.klog(noinline block: KLoggerBuilder.() -> Unit = {}): KLog =
  klog(T::class.qualifiedName ?: ROOT_PATH, block)

@KLogDSL
inline fun klog(path: String, noinline block: KLoggerBuilder.() -> Unit = {}): KLog =
  KLogImpl(contextRegistry.getLogConfig(path, block))


var contextRegistry: Registry = Registry {
  level = Level.TRACE
}

internal expect fun getenv(name: String): String?

