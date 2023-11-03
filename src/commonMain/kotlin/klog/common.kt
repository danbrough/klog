@file:Suppress("NOTHING_TO_INLINE", "KotlinRedundantDiagnosticSuppress")

package klog

enum class Level {
  TRACE, DEBUG, INFO, WARN, ERROR;
}

const val ROOT_TAG = ""


val registry = KLogContextRegistry()

fun toKLogTag(name: String): String = name

inline fun KLog.trace(msg: String, err: Throwable? = null) = log(Level.TRACE, msg, err)
inline fun KLog.debug(msg: String, err: Throwable? = null) = log(Level.DEBUG, msg, err)
inline fun KLog.info(msg: String, err: Throwable? = null) = log(Level.INFO, msg, err)
inline fun KLog.warn(msg: String, err: Throwable? = null) = log(Level.WARN, msg, err)
inline fun KLog.error(msg: String, err: Throwable? = null) = log(Level.ERROR, msg, err)

//expect inline fun <reified T : Any> T.klog(noinline config: (KLogContext.() -> Unit)? = null): KLog


fun <T : Any> T.klog(config: ConfigureContext = {context}): KLog {

  println("CREATING KLOG FOR ${this::class.qualifiedName}")
  return klog(toKLogTag(this::class.qualifiedName ?: ROOT_TAG), config)
}

fun klog(tag: String, config: ConfigureContext = {context}): KLog =DefaultLog( registry.getContext(tag,config))


