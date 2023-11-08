@file:Suppress("MemberVisibilityCanBePrivate")

package klog

@DslMarker
annotation class LogConfigDSL

@LogConfigDSL
abstract class Node(val name: String) {
  abstract val children: List<Node>

  abstract fun buildUpon(): NodeBuilder<*>
}


data class LogConfig(
  val tag: String,
  val level: Level,
  override val children: List<Node>
) : Node("logConfig") {

  override fun buildUpon() = LogConfigBuilder(tag).also { builder ->
    builder.tag = tag
    builder.level = level
    builder.children.addAll(children.map { it.buildUpon() }.toMutableList())
  }
}


@LogConfigDSL
abstract class NodeBuilder<T : Node> {
  val children = mutableListOf<NodeBuilder<*>>()

  abstract fun build(): T
}

class LogConfigBuilder(var tag: String = ROOT_TAG) : NodeBuilder<LogConfig>() {
  var level: Level = Level.TRACE


  override fun build(): LogConfig = LogConfig(tag, level, children.map { it.build() })

}

fun logConfig(block: LogConfigBuilder.() -> Unit): LogConfig =
  LogConfigBuilder().apply(block).build()
