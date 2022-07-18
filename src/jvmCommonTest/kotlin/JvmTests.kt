import org.danbrough.klog.*
import kotlin.test.Test

class JvmTests {

  private val log = klog(Level.TRACE, KLogWriters.stdOut, KMessageFormatters.verbose.colored)
//  private val log = klog("KLOG", Level.TRACE, KLogWriters.stdOut, KMessageFormatters.verbose.colored)

  @Test
  fun listProps() {
    log.error("JVM NAME: ${System.getProperty("java.vm.name")}")
    log.error("JAVA VENDOR: ${System.getProperty("java.vendor")}")
    System.getProperties().let { props ->
      props.keys.forEach {
        println("PROP: $it:\t${props[it]}")
      }
    }

  }
}
