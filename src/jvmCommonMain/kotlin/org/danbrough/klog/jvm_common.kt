package org.danbrough.klog

import kotlin.reflect.KClass


/*
val stackDepth: Int = (runCatching {
  Class.forName("android.util.Log")
  8
}.getOrNull() ?: 7).also {
  println("STACK DEPTH: $it")
}
*/


val stackDepth: Int
  get() =
    (if (System.getProperty("java.vendor").toString().contains("Android")) 10 else 9)

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
