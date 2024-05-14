@file:Suppress("unused")

package klog

import kotlin.reflect.KClass

expect fun <T : Any> loggerName(clazz: KClass<T>): String

expect fun kloggingDefault(): KLogFactory

var klogging: KLogFactory = kloggingDefault()

fun <T : KLogFactory> installLogging(logging: T, block: T.() -> Unit = {}) {
  klogging = logging
  block.invoke(logging)
}

fun kloggingStdout() {
  klogging = StdoutLogging
}

fun kloggingDisabled() {
  klogging = NOOPLogging
}

fun logger(name: String): Logger = klogging.logger(name)

inline fun <reified T : Any> T.logger(): Lazy<Logger> = lazy {
  klogging.logger(loggerName(T::class))
}