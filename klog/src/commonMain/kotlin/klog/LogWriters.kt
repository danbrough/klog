package klog

object LogWriters {
  val stdOut: LogWriter = { println(it) }
}