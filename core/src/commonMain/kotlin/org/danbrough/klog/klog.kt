@file:Suppress("unused")

package org.danbrough.klog

import org.danbrough.klog.stdout.StdoutLogging


private var logFactory: KLogFactory? = null
var klogFactory: KLogFactory
  set(value) {
    logFactory = value
  }
  get() = logFactory ?: Utils.defaultLogFactory().also { logFactory = it }


fun <T : KLogFactory> installLogging(logging: T, block: T.() -> Unit = {}) {
  klogFactory = logging
  block.invoke(logging)
}

fun kloggingStdout(block: StdoutLogging.() -> Unit = {}) {
  klogFactory = StdoutLogging().apply(block)
}

fun kloggingDisabled() {
  klogFactory = klogFactoryNOOP
}

fun logger(name: String): Logger = klogFactory.logger(name)

inline fun <reified T : Any> T.logger(): Lazy<Logger> = lazy {
  klogFactory.logger(Utils.loggerName(T::class))
}

