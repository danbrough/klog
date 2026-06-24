package org.danbrough.klog

internal val logLevels =
  propertyResolver("_") { name -> Utils.environment[name]?.let { Level.valueOf(it) } }.cached()

abstract class KLogFactory(val defaultConfiguration: KLogConfiguration) {
  //open val defaultLogLevel = Level.NONE

  open val defaultLogLevel: Level = logLevels["KLOG_LEVEL"] ?: Level.ERROR

  fun getLogLevel(logName: String): Level = logLevels[logName] ?: defaultLogLevel

  open fun logger(logName: String) =
    KLogger(logName, getLogLevel(logName), defaultConfiguration)
}


object NOOPLogFactory : KLogFactory(KLogConfiguration())

