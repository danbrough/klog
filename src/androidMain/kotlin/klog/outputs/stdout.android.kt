package klog.outputs


import android.util.Log
import klog.KLogDSL
import klog.Level
import klog.Node
import klog.NodeBuilder
import klog.ParentNode
import klog.ParentNodeBuilder
import klog.formatting.Formatter
import klog.formatting.simpleFormatter


internal val Level.androidLevel: Int
  get() = when (this) {
    Level.TRACE -> Log.VERBOSE
    Level.DEBUG -> Log.DEBUG
    Level.INFO -> Log.INFO
    Level.WARN -> Log.WARN
    Level.ERROR -> Log.ERROR
  }

data class Android(val formatter: Formatter, override val children: List<Node>) : ParentNode {
  override fun buildUpon() =
    AndroidNodeBuilder(formatter, children.map { it.buildUpon() }.toMutableList())

  override fun log(path: String, level: Level, message: String, error: Throwable?) {
    val msg = formatter.format(level, message, error)
    val line = "${msg.message} ${msg.error ?: ""}"
    Log.println(level.androidLevel, path, line)
  }
}

class AndroidNodeBuilder(
  var formatter: Formatter = simpleFormatter,
  children: MutableList<NodeBuilder<*>> = mutableListOf()
) : ParentNodeBuilder<Android>(children) {


  override fun build(): Android = Android(formatter, children.map { it.build() })
}


@KLogDSL
fun OutputsNodeBuilder.android(block: AndroidNodeBuilder.() -> Unit) {
  children.add(AndroidNodeBuilder().apply(block))
}
