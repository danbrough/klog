package klog



actual inline fun <reified T : Any> T.klog(noinline config: (KLogContext.() -> Unit)?): KLog {
  val tag = toKLogTag(this::class.toString())
  return object : KLog{
    override val log: (Level, String, Throwable?) -> Unit ={level, s, throwable ->
      println("$level:$tag:$s: err:$throwable")
    }
  }
}

