package org.danbrough.klog.test

import kotlinx.coroutines.runBlocking


class JvmMain {
  companion object {
    @JvmStatic
    fun main(args: Array<String>) = runBlocking { testMain(args) }
  }
}

actual fun test() {/*runBlocking {
    coroutineTest()
  }*/
}