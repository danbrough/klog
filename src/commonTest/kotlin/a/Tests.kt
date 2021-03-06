package  a

import org.danbrough.klog.*
import kotlin.test.Test


//create a log for package "a" and everything below


class Tests {

  @Test
  fun test() {

    println("RUNNING THE TEST")
    val log =
      klog(
        Level.TRACE,
        writer = KLogWriters.stdOut,
        messageFormatter = KMessageFormatters.verbose.colored
      )
    println("GOT LOG $log")
    log.info("an info message")
   println("WRITING A TRACE MESSAGE")
    log.trace("trace message")
    /*
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
    log.info("this is still active as it's a parent log")*/
  }

  @Test
  fun formatterTest() {
/*     val testLog = klog("a.Tests") //name will be "a.Test"

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
    customLog.info("info with exception", Exception("Example exception"))*/
  }

}