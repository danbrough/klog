package klog

data class KLogContext(val tag:String,val level: Level,val colored:Boolean){
  internal val config:ContextConfig
    get() = ContextConfig(tag,level,colored)
}
data class ContextConfig (
  var tag:String = ROOT_TAG,
  var level: Level = Level.TRACE,
  var colored:Boolean = true,){

  internal val context:KLogContext
    get() = KLogContext(tag,level,colored)
}

typealias ConfigureContext = ContextConfig.()->Unit