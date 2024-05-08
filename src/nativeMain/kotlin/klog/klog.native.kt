package klog

import kotlin.reflect.KClass

actual fun <T : Any> loggerName(clazz: KClass<T>): String =
	clazz.qualifiedName!!.substringBefore(".Companion")

actual fun kloggingDefault(): KLogFactory = StdoutLogging