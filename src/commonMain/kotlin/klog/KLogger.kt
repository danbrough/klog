package klog

data class KLogger(
  val path: String, val name: String, val level: Level, override val children: List<Node>
) : ParentNode {

  override fun buildUpon() = KLoggerBuilder(path).also { builder ->
    builder.name = name
    builder.level = level
    builder.children.addAll(children.map { it.buildUpon() }.toMutableList())
  }
}


class KLoggerBuilder(private val path: String = ROOT_PATH) :
  ParentNodeBuilder<KLogger>(mutableListOf()) {
  var level: Level = Level.TRACE
  var name: String = path

  override fun build(): KLogger = KLogger(path, name, level, children.map { it.build() })
}

@KLogDSL
fun logConfig(block: KLoggerBuilder.() -> Unit): KLogger =
  KLoggerBuilder().apply(block).build()
