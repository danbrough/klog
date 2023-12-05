package klog.outputs

import klog.LogConfigBuilder
import klog.Node
import klog.ParentNode
import klog.ParentNodeBuilder

data class Outputs(override val children: List<Node>) : ParentNode("outputs") {
  override fun buildUpon(): ParentNodeBuilder<*> {
    TODO("Not yet implemented")
  }
}

class OutputsNodeBuilder : ParentNodeBuilder<Outputs>() {
  override fun build(): Outputs = Outputs(children.map { it.build() })
}


fun LogConfigBuilder.outputs(block: OutputsNodeBuilder.() -> Unit): Outputs =
  OutputsNodeBuilder().apply(block).build()


