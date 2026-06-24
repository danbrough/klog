package org.danbrough.klog


abstract class KLogFactory {
  open val defaultLogLevel = Level.NONE
  abstract fun logger(logName: String): KLogger
}

@Suppress("ClassName")
object LOG_FACTORY_NOOP : KLogFactory() {
  override fun logger(logName: String) = NOOPLogger
}



