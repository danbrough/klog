package klog

import kotlin.reflect.KClass


actual fun platformStatementContext(): StatementContext =
  StatementContext("js", "")


actual fun createKLogFactory(): KLogFactory = object : DefaultLogFactory() {

}


actual fun KClass<*>.klogName(): String = simpleName!!

actual fun getTimeMillis(): Long = -1


