package klog

class ContextRegistry(rootContext: Context) {

  private val registry = mutableMapOf(ROOT_TAG to rootContext)

  private fun findContext(name: String): Context =
    registry[name] ?: findContext(name.substringBeforeLast('.', ROOT_TAG))

  fun get(name: String, configure: MutableContext.() -> Unit = {}): Context =
    findContext(name).toMutableContext().apply(configure).let {
      it.tag = name
      registry[name] = it
      it
    }

}

