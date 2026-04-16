package klog

open class BaseUtilsJvm {
  fun getEnv(name: String): String? = System.getenv(name)
  fun getThreadName(): String = Thread.currentThread().name
}