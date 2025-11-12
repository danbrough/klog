@file:Suppress("unused")

package klog

import klog.stdout.StdoutLogging
import kotlin.reflect.KClass

expect fun <T : Any> loggerName(clazz: KClass<T>): String

internal expect fun klogDefaultFactory(): KLogFactory

var klogFactory: KLogFactory = klogDefaultFactory()

fun <T : KLogFactory> installLogging(logging: T, block: T.() -> Unit = {}) {
  klogFactory = logging
  block.invoke(logging)
}

fun kloggingStdout() {
  klogFactory = StdoutLogging
}

fun kloggingDisabled() {
  klogFactory = NOOPLogging
}

fun logger(name: String): Logger = klogFactory.logger(name)

inline fun <reified T : Any> T.logger(): Lazy<Logger> = lazy {
  klogFactory.logger(loggerName(T::class))
}