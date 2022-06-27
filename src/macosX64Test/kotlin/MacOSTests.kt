import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.danbrough.klog.*
import kotlin.test.Test


class MacOSTests {

  @Test
  fun test1() {
    println("test1()")
    println("klogname: ${this::class.klogName()}")
    val log = klog(
      level = Level.TRACE,
      messageFormatter = KMessageFormatters.verbose.colored,
      writer = KLogWriters.stdOut
    )
    println("LOG: $log")

    log.info("an info message")

    log.trace {
      "A lazy trace message"
    }

    
    log.info("PTHREAD: ${pthread_self()} ")
    runBlocking(Dispatchers.Default) {
      log.info("inside coroutine: $this")
      log.info("PTHREAD: ${pthread_self()} ")
    }

  }
}