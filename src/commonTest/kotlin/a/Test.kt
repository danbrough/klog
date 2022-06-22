package  a

import org.danbrough.klog.*
import kotlin.test.Test


//create a log for package "a" and everything below
private val log =
  klog("a", Level.TRACE, KLogWriters.stdOut, messageFormatter = KLogMessageFormatters.verbose.colored)


class Test {
  private val testLog = klog() //name will be "a.Test"

  @Test
  fun test() {
    log.trace("trace message")
    log.debug {
      "A lazily generated debug message"
    }
    if (log.isInfoEnabled) log.info("info message")
    log.warn("warning message")
    log.error("an error message with an exception", Error("An exception"))

    testLog.trace("message from the testLog")
    testLog.level = Level.DEBUG
    testLog.trace {
      throw Error("This will not be called")
    }

    log.level = Level.INFO
    testLog.debug {
      throw Error("This will not be called as its a sub log of log")
    }
    log.info("this is visible as log.level = Level.INFO")
    testLog.level = Level.WARN
    testLog.warn("thangLog.isInfoEnabled = ${testLog.isInfoEnabled}. (Should be false)")
    log.info("this is still active as it's a parent log")
  }

  @Test
  fun formatterTest() {
    @Suppress("UNUSED_ANONYMOUS_PARAMETER")
    val customFormatter: KMessageFormatter = { name, level, msg, err, line ->
      """Name: $name Level: $level 
        Message: $msg Error:$err 
        threadName: ${line.threadName} threadID: ${line.threadID}
      """.trimMargin()
    }

    testLog.level = Level.TRACE

    val customLog = testLog.copy(messageFormatter = customFormatter.colored)
    customLog.trace("trace")
    customLog.debug("debug")
    customLog.info("info with exception", Exception("Example exception"))
  }

}