package org.danbrough.klog

interface KLogMessageContext {
  val time: Long
  val threadName: String
  val threadID: Long
  val lineNumber: Int
  val functionName: String?
  val className: String?
}


data class KLogMessageContextImpl(
  override val threadName: String,
  override val threadID: Long,
  override val lineNumber: Int = -1,
  override val functionName: String? = null,
  override val className: String? = null
) : KLogMessageContext {
  override val time: Long
    get() = getTimeMillis()
}

expect fun getTimeMillis(): Long

expect fun platformLogMessageContext(): KLogMessageContext