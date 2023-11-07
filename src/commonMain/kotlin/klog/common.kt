@file:Suppress("NOTHING_TO_INLINE", "KotlinRedundantDiagnosticSuppress")

package klog

enum class Level {
  TRACE, DEBUG, INFO, WARN, ERROR;
}

const val ROOT_TAG = ""


fun toKLogTag(name: String): String = name

inline fun KLog.trace(msg: String, err: Throwable? = null) = log(Level.TRACE, msg, err)
inline fun KLog.debug(msg: String, err: Throwable? = null) = log(Level.DEBUG, msg, err)
inline fun KLog.info(msg: String, err: Throwable? = null) = log(Level.INFO, msg, err)
inline fun KLog.warn(msg: String, err: Throwable? = null) = log(Level.WARN, msg, err)
inline fun KLog.error(msg: String, err: Throwable? = null) = log(Level.ERROR, msg, err)

inline fun <reified T : Any> T.klog(noinline conf: MutableContext.() -> Unit = {}): KLog =
  klog(T::class.qualifiedName ?: ROOT_TAG, conf)


inline fun klog(tag: String, noinline conf: MutableContext.() -> Unit = {}): KLog =
  contextRegistry.get(tag, conf).klog()

fun Context.klog(): KLog = DefaultKLog(this)
class DefaultKLog(override val context: Context):KLog{
  override fun log(level: Level, message: String, error: Throwable?) {
    println("${context.tag}:$level:$message err:$error")
  }
}

fun logContext(conf: MutableContext.() -> Unit) =
  MutableContext().apply(conf)

val contextRegistry = ContextRegistry(logContext {
  level = Level.TRACE
  formatting {
    colored = true
  }
})

internal expect fun getenv(name: String): String?

