package klog.outputs

import klog.LogConfigDSL
import klog.Node
import klog.NodeBuilder
import klog.ParentNode
import klog.ParentNodeBuilder

data class Stdout(override val children: List<Node>) : ParentNode {
  override fun buildUpon() = StdoutNodeBuilder(children.map { it.buildUpon() }.toMutableList())
}

class StdoutNodeBuilder(children: MutableList<NodeBuilder<*>> = mutableListOf()) :
  ParentNodeBuilder<Stdout>(children) {
  override fun build(): Stdout = Stdout(children.map { it.build() })
}


@LogConfigDSL
fun OutputsNodeBuilder.stdout(block: StdoutNodeBuilder.() -> Unit) {
  children.add(StdoutNodeBuilder().apply(block))
}
