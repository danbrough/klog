package klog

class Registry(root: LogConfigBuilder.() -> Unit) {

  private val registry = mutableMapOf(ROOT_PATH to logConfig(root))

  private fun findConfig(path: String): LogConfig =
    registry[path] ?: findConfig(path.substringBeforeLast('.', ROOT_PATH))

  fun getLogConfig(path: String, block: LogConfigBuilder.() -> Unit): LogConfig =
    findConfig(path).buildUpon().let { builder ->
      builder.block()
      builder.build().also {
        registry[path] = it
      }
    }


}



