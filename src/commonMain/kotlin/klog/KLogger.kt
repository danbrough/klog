package klog


interface KLog {
  fun log(level: Level, message: String, error: Throwable?)
}

data class KLogger(
  val path: String, val name: String, val level: Level, override val children: List<Node>
) : ParentNode {

  override fun buildUpon() = KLoggerBuilder(path).also { builder ->
    builder.name = name
    builder.level = level
    builder.children.addAll(children.map { it.buildUpon() }.toMutableList())
  }

  override fun log(level: Level, message: String, error: Throwable?) {
    if (level <= this.level) return
    super.log(level, message, error)
  }
}


class KLoggerBuilder(private val path: String = ROOT_PATH) :
  ParentNodeBuilder<KLogger>(mutableListOf()) {
  var level: Level = Level.TRACE
  var name: String = path

  override fun build(): KLogger = KLogger(path, name, level, children.map { it.build() })
}

@KLogDSL
fun klogger(block: KLoggerBuilder.() -> Unit): KLogger = KLoggerBuilder().apply(block).build()
