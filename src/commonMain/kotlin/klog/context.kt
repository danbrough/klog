package klog

interface BaseLogCtx<T> {
  val tag: String
  val level: Level
  fun toMutableLogContext(): MutableLogCtx<T>

  fun toLogContext(): BaseLogCtx<T> = this

  /*  operator fun invoke(thang: MutableLogCtx<T>.() -> Unit): BaseLogCtx<T> =
      toMutableLogContext().apply(thang)*/
}

interface LogCtx : BaseLogCtx<LogCtx>
interface MutableCtx : MutableLogCtx<LogCtx>


interface MutableLogCtx<T> : BaseLogCtx<T> {
  override var tag: String
  override var level: Level
  override fun toMutableLogContext(): MutableLogCtx<T> = this
}


data class DefaultCtxImpl(override var tag: String, override var level: Level) : MutableCtx,
  LogCtx {
  override fun toMutableLogContext() = copy().also {
    println("CREATED COPY OF $this")
  }
}

interface ThangContext : MutableCtx {
  var colored: Boolean
}

data class ThangContextImpl(
  override var colored: Boolean,
  private val defaultContext: MutableCtx.() -> Unit
) : MutableCtx by DefaultCtxImpl("", Level.TRACE).apply(defaultContext), ThangContext


class LogCtxRegistry<T : BaseLogCtx<T>>(rootContext: T) {

  private val registry = mutableMapOf<String, T>().also {
    it[ROOT_TAG] = rootContext
  }

  private fun findContext(name: String): T =
    registry[name] ?: findContext(name.substringBeforeLast('.', ROOT_TAG))


  @Suppress("UNCHECKED_CAST")
  fun get(name: String, configure: MutableLogCtx<T>.() -> Unit = {}): T =
    findContext(name).toMutableLogContext().apply(configure).let {
      it.tag = name
      registry[name] = it as T
      it
    }


}



