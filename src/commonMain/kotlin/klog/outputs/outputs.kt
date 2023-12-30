package klog.outputs

import klog.KLogDSL
import klog.KLoggerBuilder
import klog.Node
import klog.NodeBuilder
import klog.ParentNode
import klog.ParentNodeBuilder


data class Outputs(override val children: List<Node>) : ParentNode {
  override fun buildUpon(): ParentNodeBuilder<*> =
    OutputsNodeBuilder(children.map { it.buildUpon() }.toMutableList())
}

class OutputsNodeBuilder(children: MutableList<NodeBuilder<*>> = mutableListOf()) :
  ParentNodeBuilder<Outputs>(children) {
  override fun build(): Outputs = Outputs(children.map { it.build() })
}


@KLogDSL
fun KLoggerBuilder.outputs(block: OutputsNodeBuilder.() -> Unit) {
  children.add(OutputsNodeBuilder().apply(block))
}


