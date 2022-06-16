package org.danbrough.klog

import kotlin.reflect.KClass


@Suppress("NOTHING_TO_INLINE")
inline fun jvmLogMessageContext(): KLogMessageContext {
  val thread = Thread.currentThread()
/*  thread.stackTrace.forEach {
    println("STACK: ${it.className} method:${it.methodName} lineno:${it.lineNumber}")
  }*/
  val stackElement = thread.stackTrace[8]
  return KLogMessageContextImpl(
    thread.name,
    thread.id,
    stackElement.lineNumber,
    stackElement.methodName,
    stackElement.className
  )
}

actual fun getTimeMillis(): Long = System.currentTimeMillis()


actual fun KClass<*>.klogName():String = qualifiedName!!

