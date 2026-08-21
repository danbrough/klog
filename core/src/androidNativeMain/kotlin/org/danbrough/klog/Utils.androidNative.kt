package org.danbrough.klog


val AndroidLogWriter: KLogWriter =
  { conf: KLogConfiguration, level: Level, name: String, message: String, t: Throwable? ->
    //level.androidLogMethod(name, message, t)
  }

object AndroidNativeLogFactory : KLogFactory(KLogConfiguration(AndroidLogWriter)) {
  override var defaultLogLevel: Level = Level.TRACE

}

actual object Utils : UtilsPosix() {
  override fun defaultLogFactory(): KLogFactory = AndroidNativeLogFactory
}