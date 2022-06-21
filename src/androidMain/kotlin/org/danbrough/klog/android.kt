package org.danbrough.klog

import android.util.Log
actual fun createKLogRegistry(): KLogRegistry {

  runCatching {
    Log.v(
      KLogRegistry::class.qualifiedName,
      "Initialising stdout log with android log"
    )
    KLogWriters.stdOut = KLogWriters.androidLog
  }

  return object : DefaultLogRegistry(writer = KLogWriters.androidLog) {}
}


val KLogWriters.androidLog: KLogWriter
  get() = { name, level, msg, err ->
    when (level) {
      Level.TRACE -> Log.v(name, msg, err)
      Level.DEBUG -> Log.d(name, msg, err)
      Level.INFO -> Log.i(name, msg, err)
      Level.WARN -> Log.w(name, msg, err)
      Level.ERROR -> Log.e(name, msg, err)
    }
  }