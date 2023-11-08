@file:Suppress("NOTHING_TO_INLINE", "KotlinRedundantDiagnosticSuppress")

package klog

enum class Level {
  TRACE, DEBUG, INFO, WARN, ERROR;
}

const val ROOT_TAG = ""


inline fun KLog.trace(msg: String, err: Throwable? = null) = log(Level.TRACE, msg, err)
inline fun KLog.debug(msg: String, err: Throwable? = null) = log(Level.DEBUG, msg, err)
inline fun KLog.info(msg: String, err: Throwable? = null) = log(Level.INFO, msg, err)
inline fun KLog.warn(msg: String, err: Throwable? = null) = log(Level.WARN, msg, err)
inline fun KLog.error(msg: String, err: Throwable? = null) = log(Level.ERROR, msg, err)

inline fun <reified T : Any> T.klog(noinline block: LogConfigBuilder.() -> Unit = {}): KLog =
  klog(T::class.qualifiedName ?: ROOT_TAG, block)

inline fun klog(tag: String, noinline block: LogConfigBuilder.() -> Unit = {}): KLog =
  KLogImpl(contextRegistry.getLogConfig(tag, block))


var contextRegistry: Registry = Registry {
  level = Level.TRACE
}

internal expect fun getenv(name: String): String?

