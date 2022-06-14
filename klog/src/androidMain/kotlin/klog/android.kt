package klog

actual fun logEntryContext(): LogEntryContext =
  LogEntryContext("todo", -1L, -1, null, null)