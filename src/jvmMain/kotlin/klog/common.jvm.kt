package klog

val rootContext = KLogContext(Level.ERROR)

actual fun klog(tag:String,config:(KLogContext.()->Unit)?): KLog {
  val context = rootContext.copy().also {
    if (config != null)
      it.config()
  }
  TODO("Not yet implemented")
}

actual fun Any.klog(config: (KLogContext.() -> Unit)?): KLog {
  TODO("Not yet implemented")
}

actual inline fun <reified T : Any> T.klog(noinline config: (KLogContext.() -> Unit)?): KLog {
  TODO("Not yet implemented")
}