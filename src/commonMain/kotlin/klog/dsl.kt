@file:Suppress("MemberVisibilityCanBePrivate")

package klog

@DslMarker
annotation class KLogDSL

interface Node : KLog {
  fun buildUpon(): NodeBuilder<*>
}


interface ParentNode : Node {
  val children: List<Node>
  override fun buildUpon(): ParentNodeBuilder<*>

  override fun log(level: Level, message: String, error: Throwable?) = children.forEach {
    it.log(level, message, error)
  }


}


@KLogDSL
interface NodeBuilder<T : Node> {
  fun build(): T
}


abstract class ParentNodeBuilder<T : ParentNode>(open val children: MutableList<NodeBuilder<*>>) :
  NodeBuilder<T>


