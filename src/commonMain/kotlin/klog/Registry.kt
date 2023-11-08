package klog

class Registry(root: LogConfigBuilder.() -> Unit) {

  private val registry = mutableMapOf(ROOT_TAG to logConfig(root))

  private fun findConfig(tag: String): LogConfig =
    registry[tag] ?: findConfig(tag.substringBeforeLast('.', ROOT_TAG))

  fun getLogConfig(tag: String, block: LogConfigBuilder.() -> Unit): LogConfig =
    findConfig(tag).buildUpon().let { builder ->
      builder.block()
      builder.tag = tag
      builder.build().also {
        registry[tag] = it
      }
    }


}



