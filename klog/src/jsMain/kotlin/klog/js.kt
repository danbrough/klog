package klog

private object JSLogFactory : BaseLogFactory() {
  override fun threadID(): String = "<js>"

}

actual fun logFactory(): KLogFactory = JSLogFactory