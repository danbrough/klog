package klog


@OptIn(ExperimentalWasmJsInterop::class)
private fun getEnvWasmJS(name: String): String? = js("process.env[name] || null")


actual object Utils {
  @OptIn(ExperimentalWasmJsInterop::class)
  actual fun getEnv(name: String): String? = getEnvWasmJS(name)

  actual fun getThreadName(): String = ""
}