package klog

interface BaseLogCtx<T> {
  val tag: String
  val level: Level
  fun toMutableLogContext(): MutableLogCtx<T>

  fun toLogContext(): BaseLogCtx<T> = this

  operator fun invoke(thang: MutableLogCtx<T>.() -> Unit): BaseLogCtx<T> =
    toMutableLogContext().apply(thang)
}

interface LogCtx : BaseLogCtx<LogCtx>
interface MutableCtx : MutableLogCtx<LogCtx>


interface MutableLogCtx<T> : BaseLogCtx<T> {
  override var tag: String
  override var level: Level

  override fun toMutableLogContext(): MutableLogCtx<T>
}


data class DefaultCtxImpl(override var tag: String, override var level: Level) : MutableCtx {
  override fun toMutableLogContext() = copy()
}

interface ThangContext : MutableCtx {
  var colored: Boolean
}

data class ThangContextImpl(
  override var colored: Boolean,
  private val defaultContext: MutableCtx.() -> Unit
) : MutableCtx by DefaultCtxImpl("", Level.TRACE).apply(defaultContext), ThangContext


class LogCtxRegistry<T : BaseLogCtx<T>> {

  private val registry = mutableMapOf<String, MutableLogCtx<T>>()

  operator fun get(name: String, configure: MutableLogCtx<T>.() -> Unit = {}): T {

    registry[name]?.toMutableLogContext()?.also {
      it.configure()
      @Suppress("UNCHECKED_CAST")
      return it.toLogContext() as T
    }

    TODO()

  }
}

fun test() {
  val registry = LogCtxRegistry<LogCtx>()
  val ctx = registry["dude"]
  val t:ThangContext = ThangContextImpl(true) {
    tag = "thang"
    level = Level.TRACE
  }


}