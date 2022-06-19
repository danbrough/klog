# klog - multi-platform logging in Kotlin


## Latest version: [![](https://jitpack.io/v/danbrough/klog.svg)](https://jitpack.io/#danbrough/klog)

A logging framework for kotlin multi-platform.
Currently supporting jvm,android,js, linuxX64,linuxArm64,linuxArm32Hfp, mingwX64 but no reason it won't compile on any kotlin multiplatform target.

## Usage

```gradle 

repositories {
  maven("https://jitpack.io")
}
dependencies {
  implementation("com.github.danbrough:klog:$latestVersion")
}
```

### Basics 
```gradle 
./gradlew jvmTest --tests a.Test.* 
 ``` 
or 
```gradle 
./gradlew linuxX64Test --tests a.Test.* 
 ```

```kotlin
package a

import org.danbrough.klog.*
import kotlin.test.Test


//create a log for package "a" and everything below
private val log =
  klog("a", Level.TRACE, formatter = KLogFormatters.verbose.colored, KLogWriters.stdOut)


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
    val customFormatter: KLogFormatter = { name, level, msg, err, line ->
      """Name: $name Level: $level 
        Message: $msg Error:$err 
        threadName: ${line.threadName} threadID: ${line.threadID}
      """.trimMargin()
    }

    testLog.level = Level.TRACE

    val customLog = testLog.copy(_formatter = customFormatter.colored)
    customLog.trace("trace")
    customLog.debug("debug")
    customLog.info("info with exception", Exception("Example exception"))
  }
}
```


