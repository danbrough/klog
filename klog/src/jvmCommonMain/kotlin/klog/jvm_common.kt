package klog


@Suppress("NOTHING_TO_INLINE")
inline fun jvmLogMessageContext(): LogMessageContext {
  val thread = Thread.currentThread()
/*  thread.stackTrace.forEach {
    println("STACK: ${it.className} method:${it.methodName} lineno:${it.lineNumber}")
  }*/
  val stackElement = thread.stackTrace[9]
  return LogMessageContextImpl(
    thread.name,
    thread.id,
    stackElement.lineNumber,
    stackElement.methodName,
    stackElement.className
  )
}

