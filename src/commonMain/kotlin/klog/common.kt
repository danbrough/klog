@file:Suppress("NOTHING_TO_INLINE", "KotlinRedundantDiagnosticSuppress")

package klog

enum class Level {
  TRACE, DEBUG, INFO, WARN, ERROR;
}

const val ROOT_TAG = ""

val registry = ContextRegistry()

fun toKLogTag(name: String): String = name

inline fun KLog.trace(msg: String, err: Throwable? = null) = log(Level.TRACE, msg, err)
inline fun KLog.debug(msg: String, err: Throwable? = null) = log(Level.DEBUG, msg, err)
inline fun KLog.info(msg: String, err: Throwable? = null) = log(Level.INFO, msg, err)
inline fun KLog.warn(msg: String, err: Throwable? = null) = log(Level.WARN, msg, err)
inline fun KLog.error(msg: String, err: Throwable? = null) = log(Level.ERROR, msg, err)


fun <T : Any> T.klog(config: ConfigureContext = { context }) =
  klog(toKLogTag(this::class.qualifiedName ?: ROOT_TAG), config)

fun klog(tag: String, config: ConfigureContext = { context }): KLog =
  DefaultLog(registry.getContext(tag, config))

internal expect fun getenv(name: String): String?

