# Kotlin multiplatform logging facade

Light-weight logging facade for kotlin multi-platform.

Implementations default to simple stdout loggers on native targets,
[SLFJ](https://www.slf4j.org/) on jvm and
the [Android Log](https://developer.android.com/reference/android/util/Log) on android.

Alternatively [kotlin-logging](https://github.com/oshai/kotlin-logging) is available as well
or you can trivially wire it up to anything with a similar api.

```kotlin

/**
 *Initial demo builds are available via maven at:
 * repositories {
 *    maven("https://maven.danbrough.org")
 * }
 * implementation("org.danbrough.klog:klog:0.0.3-beta02")
 */


import klog.logging

class Foo {
	private val log by logging()

	fun test() {
		log.trace { "A trace call" } //or debug,info,warn,error ..
		log.error(Exception("An exception")) { "An error occurred" }
	}
}


```







