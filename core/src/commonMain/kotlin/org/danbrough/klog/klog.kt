@file:Suppress("unused")

package org.danbrough.klog

import org.danbrough.klog.std.StandardLogFactory

interface Named {
  val name: String
}

private var logFactory: KLogFactory? = null
var klogFactory: KLogFactory
  set(value) {
    logFactory = value
  }
  get() = logFactory ?: Utils.defaultLogFactory().also { logFactory = it }


fun <T : KLogFactory> installLogging(logging: T, block: T.() -> Unit = {}) {
  if (klogFactory != logging) klogFactory = logging
  block.invoke(logging)
}

fun kloggingStandard(block: StandardLogFactory.() -> Unit = {}) {
  logFactory = null
  (klogFactory as StandardLogFactory).block()
}

fun kloggingDisabled() {
  klogFactory = LOG_FACTORY_NOOP
}

fun logger(name: String): KLogger = klogFactory.logger(name)

inline fun <reified T : Any> T.logger(): Lazy<KLogger> = lazy {
  klogFactory.logger(Utils.loggerName(T::class))
}

