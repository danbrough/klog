package org.danbrough.klog

object LogWriters {
  val stdOut: LogWriter = { println(it) }
}