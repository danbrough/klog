package klog


abstract class KLogFactory {
  abstract fun logger(logName: String): Logger
}


internal object NOOPLogging : KLogFactory() {

  object NOOPLogger : DelegatingLogger {
    override var log: LoggerMethod = { _, _, _, _ -> }
    override val name: String = ""
  }

  override fun logger(logName: String) = NOOPLogger
}



