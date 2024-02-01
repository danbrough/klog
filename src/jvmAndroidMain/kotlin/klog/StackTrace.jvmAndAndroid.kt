package klog

actual fun getStackTrace(err: Throwable?): StackTrace? {

  val index = if (err != null) 0 else 2
  val error = err ?: Exception("")
  error.stackTrace[index].also {
    println("STACK TRACE: fileName:${it.fileName} class:${it.className} lineNo:${it.lineNumber} method:${it.methodName}")
  }
  return null
}