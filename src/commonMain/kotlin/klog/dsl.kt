@file:Suppress("MemberVisibilityCanBePrivate")

package klog

@DslMarker
annotation class LogConfigDSL

@LogConfigDSL
abstract class Node(val name: String) {
  abstract fun buildUpon(): NodeBuilder<*>
}

abstract class ParentNode(name: String) : Node(name) {
  abstract val children: List<Node>
  abstract override fun buildUpon(): ParentNodeBuilder<*>
}

data class LogConfig(
  val tag: String,
  val level: Level,
  override val children: List<Node>
) : ParentNode("logConfig") {

  override fun buildUpon() = LogConfigBuilder(tag).also { builder ->
    builder.tag = tag
    builder.level = level
    builder.children.addAll(children.map { it.buildUpon() }.toMutableList())
  }
}


@LogConfigDSL
interface NodeBuilder<T : Node> {
  fun build(): T
}

abstract class ParentNodeBuilder<T : ParentNode> : NodeBuilder<T> {
  val children = mutableListOf<NodeBuilder<*>>()
}

class LogConfigBuilder(var tag: String = ROOT_TAG) : ParentNodeBuilder<LogConfig>() {
  var level: Level = Level.TRACE

  override fun build(): LogConfig = LogConfig(tag, level, children.map { it.build() })

}

fun logConfig(block: LogConfigBuilder.() -> Unit): LogConfig =
  LogConfigBuilder().apply(block).build()
