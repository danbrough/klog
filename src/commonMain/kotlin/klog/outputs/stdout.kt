package klog.outputs

import klog.Node
import klog.NodeBuilder
import klog.ParentNode
import klog.ParentNodeBuilder

data class Stdout(override val children: List<Node>) : ParentNode() {
  override fun buildUpon(): ParentNodeBuilder<*> =
    StdoutNodeBuilder(children.map { it.buildUpon() }.toMutableList())
}

class StdoutNodeBuilder(override val children: MutableList<NodeBuilder<*>> = mutableListOf()) :
  ParentNodeBuilder<Stdout>() {
  override fun build(): Stdout = Stdout(children.map { it.build() })
}


fun OutputsNodeBuilder.stdout(block: StdoutNodeBuilder.() -> Unit) {
  children.add(StdoutNodeBuilder().apply(block))
}
