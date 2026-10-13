package org.danbrough.klog

internal val logLevels =
  propertyResolver { name -> Utils.environment[name]?.let { Level.valueOf(it) } }.defaultToParent(
    parentNameResolverDelimited("_")
  )

abstract class KLogFactory(val defaultConfiguration: KLogConfiguration) {
  //open val defaultLogLevel = Level.NONE

  open var defaultLogLevel: Level = logLevels["KLOG_LEVEL"] ?: Level.ERROR

  fun getLogLevel(logName: String): Level = logLevels["KLOG_$logName"] ?: defaultLogLevel

  open fun logger(logName: String) =
    KLogger(logName, getLogLevel(logName), defaultConfiguration)
}


object NOOPLogFactory : KLogFactory(KLogConfiguration())

