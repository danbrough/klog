@file:Suppress("MemberVisibilityCanBePrivate")

package klog.outputs

import klog.KLogDSL
import klog.Level
import klog.Node
import klog.NodeBuilder
import klog.ParentNode
import klog.ParentNodeBuilder
import klog.formatting.Formatter
import klog.formatting.simpleFormatter

data class Stdout(val formatter: Formatter, override val children: List<Node>) : ParentNode {
  override fun buildUpon() = StdoutNodeBuilder(children.map { it.buildUpon() }.toMutableList())

  override fun log(level: Level, message: String, error: Throwable?) {
    val msg = formatter.format(level, message, error)
    println("${msg.level}: ${msg.message} ${msg.error ?: ""}")
  }
}

class StdoutNodeBuilder(children: MutableList<NodeBuilder<*>> = mutableListOf()) :
  ParentNodeBuilder<Stdout>(children) {

  var formatter: Formatter = simpleFormatter

  override fun build(): Stdout = Stdout(formatter, children.map { it.build() })
}


@KLogDSL
fun OutputsNodeBuilder.stdout(block: StdoutNodeBuilder.() -> Unit) {
  children.add(StdoutNodeBuilder().apply(block))
}
