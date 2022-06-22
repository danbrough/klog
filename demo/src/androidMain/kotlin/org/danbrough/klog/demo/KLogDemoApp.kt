package org.danbrough.klog.demo

import android.app.Activity
import android.app.Application
import org.danbrough.klog.*

class KLogDemoApp : Application() {
  private val log = klog(
    "org.danbrough.klog.demo",
    level = Level.TRACE,
    formatter = KLogFormatters.verbose.colored,
    writer = KLogWriters.androidLog
  )

}