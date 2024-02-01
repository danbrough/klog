@file:Suppress("MemberVisibilityCanBePrivate")

package klog

@DslMarker
annotation class KLogDSL

interface KLog {
  fun log(level: Level, message: String, error: Throwable?)
}

interface Node {
  fun buildUpon(): NodeBuilder<*>

  fun log(path: String, level: Level, message: String, error: Throwable?)

}


interface ParentNode : Node {
  val children: List<Node>
  override fun buildUpon(): ParentNodeBuilder<*>

  override fun log(path: String, level: Level, message: String, error: Throwable?) =
    children.forEach {
      it.log(path, level, message, error)
    }
}


@KLogDSL
interface NodeBuilder<T : Node> {
  fun build(): T
}


abstract class ParentNodeBuilder<T : ParentNode>(open val children: MutableList<NodeBuilder<*>> = mutableListOf()) :
  NodeBuilder<T>


