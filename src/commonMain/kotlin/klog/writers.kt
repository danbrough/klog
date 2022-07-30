package klog

import kotlin.native.concurrent.ThreadLocal

typealias KLogWriter = (String, Level, String, Throwable?) -> Unit


@ThreadLocal
object KLogWriters {
  val noop: KLogWriter = { _, _, _, _ -> }
  var stdOut: KLogWriter = { _, _, msg, _ -> println(msg) }
}