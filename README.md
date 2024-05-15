# Kotlin multiplatform logging facade

Light-weight logging facade for kotlin multi-platform.

Implementations default to simple stdout loggers on native targets,
[SLFJ](https://www.slf4j.org/) on jvm and
the [Android Log](https://developer.android.com/reference/android/util/Log) on android.

Alternatively [kotlin-logging](https://github.com/oshai/kotlin-logging) is available as well
or you can trivially wire it up to anything with a similar api.

Initial demo builds are available via maven at:

```kotlin
repositories {
  maven("https://maven.danbrough.org")
}

dependencies {
  implementation("org.danbrough.klog:core:0.0.3-beta01")
}
```

The core KMP library has the api and basic stdout loggers which are the default implementations,
apart from android which delegates to the android logger.
If you would like to delegate to [kotlin-logging](https://github.com/oshai/kotlin-logging) then add
the `implementation("org.danbrough.klog:oshahi:..")` KMP library to commonMain and initialise your
application
with `klog.kloggingOshai{}`.

To delegate to [SLFJ](https://www.slf4j.org/) use `implementation("org.danbrough.klog:slf4j:..")` in
you jvm or android application.
Initialise it with `klog.kloggingSlf4j{}` and configure your slf4j implementation as per usual.

```kotlin
package stuff

import klog.logging

class Foo {
  private val log by logger() //logger with name "stuff.Foo"
  //alternatively:
  //private val log = klog.logger("stuff.Foo")

  fun test() {
    log.trace { "A trace call" } //or debug,info,warn,error ..
    log.error(Exception("An exception")) { "An error occurred" }
  }
}

```







