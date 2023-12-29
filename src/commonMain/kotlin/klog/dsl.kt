@file:Suppress("MemberVisibilityCanBePrivate")

package klog

@DslMarker
annotation class KLogDSL

interface Node {
  fun buildUpon(): NodeBuilder<*>
}

interface ParentNode : Node {
  val children: List<Node>
  override fun buildUpon(): ParentNodeBuilder<*>


}


@KLogDSL
interface NodeBuilder<T : Node> {
  fun build(): T
}

abstract class ParentNodeBuilder<T : ParentNode>(open val children: MutableList<NodeBuilder<*>>) :
  NodeBuilder<T>


