package klog

class Registry(root: KLoggerBuilder.() -> Unit) {

  private val registry = mutableMapOf(ROOT_PATH to logConfig(root))

  private fun findConfig(path: String): KLogger =
    registry[path] ?: findConfig(path.substringBeforeLast('.', ROOT_PATH))

  fun getLogConfig(path: String, block: KLoggerBuilder.() -> Unit): KLogger =
    findConfig(path).buildUpon().run {
      block()
      build().also {
        registry[path] = it
      }
    }


}



