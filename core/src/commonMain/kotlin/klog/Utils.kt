package klog


expect object Utils {
  fun getEnv(name: String): String?
  fun getThreadName(): String
}
