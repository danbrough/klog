package klog

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import platform.posix.pthread_self

actual fun initLogging() {

  val test: (Int)->Unit = {
    println("CURRENT THREAD: $it: ${pthread_self()}")
  }

  test(1)
  runBlocking(Dispatchers.Default){
    test(2)
    launch(Dispatchers.Default) {
      test(3)
    }
    withContext(Dispatchers.Main){
      test(4)
    }
  }

  test(5)

}