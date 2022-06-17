package org.danbrough.klog


actual fun createKLogRegistry(): KLogRegistry {
  KLogWriters.stdOut = KLogWriters.androidLog
  return object : DefaultLogRegistry(writer = KLogWriters.androidLog) {}
}


val KLogWriters.androidLog: KLogWriter
  get() = { name, level, msg, err ->
    when (level) {
      Level.TRACE -> android.util.Log.v(name, msg, err)
      Level.DEBUG -> android.util.Log.d(name, msg, err)
      Level.INFO -> android.util.Log.i(name, msg, err)
      Level.WARN -> android.util.Log.w(name, msg, err)
      Level.ERROR -> android.util.Log.e(name, msg, err)
    }
  }