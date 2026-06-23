package org.danbrough.klog

import org.danbrough.klog.std.BaseStandardLogFactory


abstract class KLogFactory {
  open var defaultLogLevel = Level.NONE
  abstract fun logger(logName: String): KLogger
}

@Suppress("ClassName")
object LOG_FACTORY_NOOP : KLogFactory() {
  override fun logger(logName: String) = NOOPLogger
}

internal val klogFactoryStandard = BaseStandardLogFactory()


