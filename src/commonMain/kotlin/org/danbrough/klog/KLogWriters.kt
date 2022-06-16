package org.danbrough.klog

object KLogWriters {
  val stdOut: KLogWriter = { println(it) }
  val noop: KLogWriter = {}
}