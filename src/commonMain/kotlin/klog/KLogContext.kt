package klog

data class KLogContext(val tag:String,val level: Level,val colored:Boolean)

data class KLogContextConfig (
  var tag:String = ROOT_TAG,
  var level: Level = Level.TRACE,
  var colored:Boolean = true)

typealias ConfigureContext = KLogContextConfig.()->Unit

internal val KLogContext.config: KLogContextConfig
  get() = KLogContextConfig(tag,level,colored)

internal val KLogContextConfig.context:KLogContext
  get() = KLogContext(tag,level,colored)





