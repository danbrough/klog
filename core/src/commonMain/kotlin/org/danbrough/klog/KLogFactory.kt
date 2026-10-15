package org.danbrough.klog

internal val logLevels =
  propertyResolver { name ->
    Utils.environment["KLOG_${name}_LEVEL"]?.let { Level.valueOf(it.uppercase()) }
  }.defaultToParent(
    parentNameResolverDelimited("_")
  )

abstract class KLogFactory(val defaultConfiguration: KLogConfiguration) {
  //open val defaultLogLevel = Level.NONE

  open var defaultLogLevel: Level = Utils.environment["KLOG_LEVEL"]?.let { Level.valueOf(it.uppercase()) } ?: Level.ERROR

  fun getLogLevel(logName: String): Level = logLevels[logName] ?: defaultLogLevel

  open fun logger(logName: String) =
    KLogger(logName, getLogLevel(logName), defaultConfiguration)
}


object NOOPLogFactory : KLogFactory(KLogConfiguration())

