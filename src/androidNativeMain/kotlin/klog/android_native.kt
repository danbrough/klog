package klog


val KLogWriters.androidLog: KLogWriter
  get() = { name, level, msg, err ->
    when (level) {
      Level.TRACE ->
        platform.android.__android_log_write(
          platform.android.ANDROID_LOG_VERBOSE.toInt(),
          name,
          msg
        )


      Level.DEBUG -> platform.android.__android_log_write(
        platform.android.ANDROID_LOG_DEBUG.toInt(),
        name,
        msg
      )

      Level.INFO -> platform.android.__android_log_write(
        platform.android.ANDROID_LOG_INFO.toInt(),
        name,
        msg
      )

      Level.WARN -> platform.android.__android_log_write(
        platform.android.ANDROID_LOG_WARN.toInt(),
        name,
        msg
      )

      Level.ERROR -> platform.android.__android_log_write(
        platform.android.ANDROID_LOG_ERROR.toInt(),
        name,
        msg
      )
    }
  }


