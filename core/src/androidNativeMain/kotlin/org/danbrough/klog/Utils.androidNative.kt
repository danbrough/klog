package org.danbrough.klog

import platform.android.__android_log_print


val Level.androidLogLevel: Int
  get() = when (this) {
    Level.TRACE -> 2
    Level.DEBUG -> 3
    Level.INFO -> 4
    Level.WARN -> 5
    Level.ERROR -> 6
    else -> -1
  }

val AndroidLogWriter: KLogWriter =
  { conf: KLogConfiguration, level: Level, name: String, message: String, t: Throwable? ->
    __android_log_print(
      level.androidLogLevel,
      name,
      if (t != null) "$message: ${t.stackTraceToString()}" else message
    )
  }

object AndroidNativeLogFactory : KLogFactory(KLogConfiguration(AndroidLogWriter)) {
  override var defaultLogLevel: Level = Level.TRACE

}

actual object Utils : UtilsPosix() {
  override fun defaultLogFactory(): KLogFactory = AndroidNativeLogFactory
}