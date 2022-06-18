package org.danbrough.klog

import kotlin.reflect.KClass


@Suppress("NOTHING_TO_INLINE")
actual inline fun platformStatementContext(): StatementContext =
  Thread.currentThread().let {
    val stackElement = it.stackTrace[7]
    StatementContext(
      it.name, it.id,
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
