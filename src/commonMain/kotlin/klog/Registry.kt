package klog

class Registry(root: KLoggerBuilder.() -> Unit) {

  private val registry = mutableMapOf(ROOT_PATH to KLoggerBuilder().apply(root).build())

  private fun findConfig(path: String): KLogger =
    registry[path] ?: findConfig(path.substringBeforeLast('.', ROOT_PATH))

  fun getLogger(path: String, block: KLoggerBuilder.() -> Unit): KLogger =
    findConfig(path).buildUpon().run {
      block()
      build().also {
        registry[path] = it
      }
    }


}



