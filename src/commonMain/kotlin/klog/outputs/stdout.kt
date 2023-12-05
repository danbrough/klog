package klog.outputs

import klog.Node
import klog.ParentNode
import klog.ParentNodeBuilder

data class Stdout(override val children: List<Node>) : ParentNode("stdout") {
  override fun buildUpon(): ParentNodeBuilder<*> {
    TODO("Not yet implemented")
  }
}

class StdoutNodeBuilder : ParentNodeBuilder<Stdout>() {
  override fun build(): Stdout = Stdout(children.map { it.build() })
}


fun OutputsNodeBuilder.stdout(block: StdoutNodeBuilder.() -> Unit): Stdout =
  StdoutNodeBuilder().apply(block).build()
