package klog

data class StackTrace(
  val lineNo: Int = -1, val methodName: String? = null, val className: String? = null
)

expect fun getStackTrace(err: Throwable? = null): StackTrace?

