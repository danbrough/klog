package org.danbrough.klog

import kotlin.reflect.KClass


actual fun platformStatementContext(): StatementContext =
  StatementContext("js", 0)


actual fun createKLogRegistry(): KLogRegistry = object : DefaultLogRegistry() {

}


actual fun KClass<*>.klogName():String = simpleName!!

actual fun getTimeMillis(): Long = -1


