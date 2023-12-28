package klog.outputs

import klog.LogConfigBuilder
import klog.LogConfigDSL
import klog.Node
import klog.NodeBuilder
import klog.ParentNode
import klog.ParentNodeBuilder

data class Outputs(override val children: List<Node>) : ParentNode {
  override fun buildUpon(): ParentNodeBuilder<*> = OutputsNodeBuilder()
}

class OutputsNodeBuilder(children: MutableList<NodeBuilder<*>> = mutableListOf()) :
  ParentNodeBuilder<Outputs>(children) {
  override fun build(): Outputs = Outputs(children.map { it.build() })
}


@LogConfigDSL
fun LogConfigBuilder.outputs(block: OutputsNodeBuilder.() -> Unit) {
  children.add(OutputsNodeBuilder().apply(block))
}


