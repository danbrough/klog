package klog

class Registry(root: KLoggerBuilder.() -> Unit) {

  private val registry = mutableMapOf(ROOT_PATH to KLoggerBuilder().apply(root).build())

  private fun findConfig(path: String): KLogger =
    registry[path] ?: findConfig(path.substringBeforeLast('.', ROOT_PATH))

  fun getLogger(path: String, block: KLoggerBuilder.() -> Unit): KLogger {
    println("GETTING LOGGER FOR $path")
    return findConfig(path).buildUpon().run {
      this.path = path
      block()
      build().also {
        registry[path] = it
      }
    }
  }


}



