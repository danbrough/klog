@file:Suppress("NOTHING_TO_INLINE")

package klog

actual inline fun <reified T : Any> T.klog(noinline config: (KLogContext.() -> Unit)?): KLog {
  TODO("Not yet implemented")
}

inline fun test(){

}