package org.danbrough.klog

import android.util.Log
import java.lang.reflect.Modifier

class AndroidLogger(name: String) : LoggerBase(name) {

  override var log: LoggerMethod = { level, _, message, t ->
    val androidLevel = when (level) {
      Level.TRACE -> Log.VERBOSE
      Level.DEBUG -> Log.DEBUG
      Level.INFO -> Log.INFO
      Level.WARN -> Log.WARN
      Level.ERROR -> Log.ERROR
      else -> -1
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
  override var level: Level = Level.TRACE
}

object AndroidLogging : KLogFactory() {
  override fun logger(logName: String) = AndroidLogger(logName)
}

fun kloggingAndroid(block: AndroidLogging.() -> Unit = {}) {
  installLogging(AndroidLogging, block)
}


private fun <T : Any> unwrapCompanionClass(clazz: Class<T>): Class<*> {
  return clazz.enclosingClass?.let { enclosingClass ->
    try {
      enclosingClass.declaredFields.find { field ->
          field.name == clazz.simpleName && Modifier.isStatic(field.modifiers) && field.type == clazz
        }?.run { enclosingClass }
    } catch (se: SecurityException) {
      // The security manager isn't properly set up, so it won't be possible
      // to search for the target declared field.
      null
    }
  } ?: clazz
}



