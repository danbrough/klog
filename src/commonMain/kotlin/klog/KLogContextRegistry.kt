package klog


class KLogContextRegistry {

  private val registryMap = mutableMapOf<String, KLogContext>()


  //TODO fix up defaults
  fun getContext(name: String, config: ConfigureContext = { context }):KLogContext =
    registryMap[name] ?: KLogContext(name, Level.TRACE, true).config.let {
      it.config()
      it.context
    }


}