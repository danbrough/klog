package klog

fun interface MessageFormatter {
  fun formatMessage(logger: KLogger, message: String): String
}