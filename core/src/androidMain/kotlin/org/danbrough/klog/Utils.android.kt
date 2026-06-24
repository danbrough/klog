package org.danbrough.klog

import android.util.Log
import java.lang.reflect.Modifier

/*
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

*/

val Level.androidLogLevel: Int
  get() = when (this) {
    Level.TRACE -> Log.VERBOSE
    Level.DEBUG -> Log.DEBUG
    Level.INFO -> Log.INFO
    Level.WARN -> Log.WARN
    Level.ERROR -> Log.ERROR
    else -> -1
  }

internal val Level.androidLogMethod: (String, String?, Throwable?) -> Unit
  get() = when (this) {
    Level.TRACE -> Log::v
    Level.DEBUG -> Log::d
    Level.INFO -> Log::i
    Level.WARN -> Log::w
    Level.ERROR -> Log::e
    else -> error("Invalid log level: $this")
  }


val AndroidLogWriter: KLogWriter =
  { conf: KLogConfiguration, level: Level, name: String, message: String, t: Throwable? ->
    level.androidLogMethod(name, message, t)
  }


object AndroidLogFactory : KLogFactory(KLogConfiguration(AndroidLogWriter))


actual object Utils : BaseUtilsJvm() {
  override fun defaultLogFactory(): KLogFactory = AndroidLogFactory
}