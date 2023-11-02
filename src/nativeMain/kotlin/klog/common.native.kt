package klog

actual fun klog(tag:String,config:(KLogContext.()->Unit)?): KLog {
  TODO("Not yet implemented")
}

actual inline fun <reified T : Any> T.klog(noinline config: (KLogContext.() -> Unit)?): KLog {
  TODO("Not yet implemented")
}
