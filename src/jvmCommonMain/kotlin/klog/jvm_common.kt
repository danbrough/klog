package klog

import kotlin.reflect.KClass

val stackDepth: Int
  get() =
    (if (System.getProperty("java.vendor").toString().contains("Android")) 7 else 6)

@Suppress("NOTHING_TO_INLINE")
actual inline fun platformStatementContext(): StatementContext =
  Thread.currentThread().let {
    val stackElement = it.stackTrace[stackDepth]
    StatementContext(
      it.name, it.name,
      StatementContext.LineContext(
        stackElement.lineNumber,
        stackElement.methodName,
        stackElement.className,
        stackElement.fileName,
      )
    )
  }


actual fun getTimeMillis(): Long = System.currentTimeMillis()


actual fun KClass<*>.klogName(): String = qualifiedName!!.removeSuffix(".Companion")
