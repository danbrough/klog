package klog.outputs

import klog.LogConfigBuilder
import klog.Node
import klog.ParentNode
import klog.ParentNodeBuilder

data class Outputs(override val children: List<Node>) : ParentNode() {
  override fun buildUpon(): ParentNodeBuilder<*> = OutputsNodeBuilder()
}

class OutputsNodeBuilder : ParentNodeBuilder<Outputs>() {
  override fun build(): Outputs = Outputs(children.map { it.build() })
}


fun LogConfigBuilder.outputs(block: OutputsNodeBuilder.() -> Unit) {
  children.add(OutputsNodeBuilder().apply(block))
}


