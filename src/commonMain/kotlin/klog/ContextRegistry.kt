package klog


class ContextRegistry {

  private val registryMap = mutableMapOf<String, LogContext>()


  private fun findContext(name: String): LogContext {

    registryMap[name]?.also { return it }

    if (name == ROOT_TAG) return LogContext(ROOT_TAG, Level.TRACE, false).also {
      registryMap[ROOT_TAG] = it
    }

    return findContext(name.substringBeforeLast('.', ROOT_TAG))
  }

  fun getContext(name: String, config: ConfigureContext = { context }): LogContext =
    findContext(name.lowercase()).config.let {
      it.tag = name
      it.config()
      it.context
    }

}