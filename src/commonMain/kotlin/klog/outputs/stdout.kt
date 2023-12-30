@file:Suppress("MemberVisibilityCanBePrivate")

package klog.outputs

import klog.KLogDSL
import klog.Level
import klog.Node
import klog.NodeBuilder
import klog.ParentNode
import klog.ParentNodeBuilder

data class Stdout(val colored: Boolean, override val children: List<Node>) : ParentNode {
  override fun buildUpon() = StdoutNodeBuilder(children.map { it.buildUpon() }.toMutableList())

  override fun log(level: Level, message: String, error: Throwable?) {
    println("$level: $message ${error ?: ""}")
  }
}

class StdoutNodeBuilder(children: MutableList<NodeBuilder<*>> = mutableListOf()) :
  ParentNodeBuilder<Stdout>(children) {

  var colored: Boolean = false

  override fun build(): Stdout = Stdout(colored, children.map { it.build() })
}


@KLogDSL
fun OutputsNodeBuilder.stdout(block: StdoutNodeBuilder.() -> Unit) {
  children.add(StdoutNodeBuilder().apply(block))
}
