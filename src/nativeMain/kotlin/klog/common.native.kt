package klog


actual inline fun <reified T : Any> T.klog(noinline config: (KLogContext.() -> Unit)?): KLog {
  TODO("Not yet implemented")
}
