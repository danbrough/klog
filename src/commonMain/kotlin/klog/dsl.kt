@file:Suppress("MemberVisibilityCanBePrivate")

package klog

@DslMarker
annotation class LogConfigDSL

@LogConfigDSL
interface Node {
  fun buildUpon(): NodeBuilder<*>
}

interface ParentNode : Node {
  val children: List<Node>
  override fun buildUpon(): ParentNodeBuilder<*>
}

data class LogConfig(
  val path: String, val name: String, val level: Level, override val children: List<Node>
) : ParentNode {

  override fun buildUpon() = LogConfigBuilder(path).also { builder ->
    builder.name = name
    builder.level = level
    builder.children.addAll(children.map { it.buildUpon() }.toMutableList())
  }
}


@LogConfigDSL
interface NodeBuilder<T : Node> {
  fun build(): T
}

abstract class ParentNodeBuilder<T : ParentNode>(open val children: MutableList<NodeBuilder<*>>) :
  NodeBuilder<T>


class LogConfigBuilder(val path: String = ROOT_PATH) :
  ParentNodeBuilder<LogConfig>(mutableListOf()) {
  var level: Level = Level.TRACE
  var name: String = path

  override fun build(): LogConfig = LogConfig(path, name, level, children.map { it.build() })

}

fun logConfig(block: LogConfigBuilder.() -> Unit): LogConfig =
  LogConfigBuilder().apply(block).build()
