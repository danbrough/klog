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

```kotlin
package a 

import org.danbrough.klog.*

//create a log for package "a" and everything below
val log = klog("a") 

log.trace("trace message")
log.debug {
  "A lazily generated debug message"
}
if (log.isInfoEnabled)  log.info("info message")
log.warn("warning message")
log.error("an error message with an exception",Error("An exception"))

```
