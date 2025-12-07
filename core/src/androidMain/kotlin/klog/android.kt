package klog

import android.util.Log
import java.lang.reflect.Modifier
import kotlin.reflect.KClass

class AndroidLogger(override val name: String) : DelegatingLogger {

  override var log: LoggerMethod = { level, _, message, t ->
    val androidLevel = when (level) {
      Logger.Level.TRACE -> Log.VERBOSE
      Logger.Level.DEBUG -> Log.DEBUG
      Logger.Level.INFO -> Log.INFO
      Logger.Level.WARN -> Log.WARN
      Logger.Level.ERROR -> Log.ERROR
      Logger.Level.NONE -> -1
    }

    if (Log.isLoggable(name, androidLevel)) {
      when (androidLevel) {
        Log.VERBOSE -> Log.v(name, message().toString(), t)
        Log.DEBUG -> Log.d(name, message().toString(), t)
        Log.INFO -> Log.i(name, message().toString(), t)
        Log.WARN -> Log.w(name, message().toString(), t)
        Log.ERROR -> Log.e(name, message().toString(), t)
        else -> Unit
      }
    }
  }
  override var level: Logger.Level = Logger.Level.TRACE
}

object AndroidLogging : KLogFactory() {
  override fun logger(logName: String) = AndroidLogger(logName)
}

fun kloggingAndroid(block: AndroidLogging.() -> Unit = {}) {
  installLogging(AndroidLogging, block)
}

actual fun klogDefaultFactory(): KLogFactory = AndroidLogging

actual fun <T : Any> loggerName(clazz: KClass<T>): String = unwrapCompanionClass(clazz.java).name

private fun <T : Any> unwrapCompanionClass(clazz: Class<T>): Class<*> {
  return clazz.enclosingClass?.let { enclosingClass ->
    try {
      enclosingClass.declaredFields
        .find { field ->
          field.name == clazz.simpleName &&
              Modifier.isStatic(field.modifiers) &&
              field.type == clazz
        }
        ?.run { enclosingClass }
    } catch (se: SecurityException) {
      // The security manager isn't properly set up, so it won't be possible
      // to search for the target declared field.
      null
    }
  } ?: clazz
}



