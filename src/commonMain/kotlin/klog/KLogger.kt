package klog


data class KLogger(
  val path: String, val name: String, val level: Level, override val children: List<Node>
) : ParentNode {

  override fun buildUpon() = KLoggerBuilder().also { builder ->
    builder.path = ROOT_PATH
    builder.name = name
    builder.level = level
    builder.children.addAll(children.map { it.buildUpon() }.toMutableList())
  }

  override fun log(level: Level, message: String, error: Throwable?) {
    if (level < this.level) return
    super.log(level, message, error)
  }
}


class KLoggerBuilder :
  ParentNodeBuilder<KLogger>() {
  var path: String = ROOT_PATH
  var level: Level = Level.TRACE
  var name: String = path

  override fun build(): KLogger = KLogger(path, name, level, children.map { it.build() })

}
