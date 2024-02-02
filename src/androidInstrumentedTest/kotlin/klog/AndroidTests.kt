package klog

import androidx.test.platform.app.InstrumentationRegistry
import klog.formatting.colored
import klog.formatting.simpleFormatter
import klog.outputs.android
import klog.outputs.outputs
import org.junit.Test

private val rootLog = klog(ROOT_PATH) {
  level = Level.TRACE
  name = "KLogTests"

  outputs {
    android {
      formatter = simpleFormatter.colored
    }
  }


}

class AndroidTests {


  @Test
  fun test1() {
    val context = InstrumentationRegistry.getInstrumentation().context
    rootLog.trace("test1()")
    rootLog.debug("test1()")
    rootLog.info("the context is $context")
    rootLog.warn("test1()")
    rootLog.error("test1()", Exception("test_exception"))

    val testLog = klog("klog")
    testLog.warn("test1() from testLog")

    testLog.debug("STACKTRACE: ${getStackTrace()}")
  }
}