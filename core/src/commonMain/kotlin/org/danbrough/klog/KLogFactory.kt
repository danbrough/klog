package org.danbrough.klog

import org.danbrough.klog.std.BaseStandardLogFactory


abstract class KLogFactory {
  open var defaultLogLevel = Level.NONE
  abstract fun logger(logName: String): KLogger
}

val klogFactoryNOOP = object : KLogFactory() {
  override fun logger(logName: String) = NOOPLogger
}

val klogFactoryStandard = BaseStandardLogFactory()


