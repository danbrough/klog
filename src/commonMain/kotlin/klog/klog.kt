package klog

import kotlin.reflect.KClass

expect fun <T : Any> loggerName(clazz: KClass<T>): String
expect fun kloggingDefault(): KLogFactory

var klogging: KLogFactory = kloggingDefault()

fun installLogging(logging: KLogFactory) {
	klogging = logging
}

fun kloggingStdout() {
	klogging = StdoutLogging
}

fun kloggingDisabled() {
	klogging = NOOPLogging
}

fun kloggingOshai() {
	klogging = OshaiLoggingFactory
}

/*
fun <R : Any> R.logger(): Lazy<KLogger> {
	return lazy {
		TODO()
		//Logger.getLogger(unwrapCompanionClass(this.javaClass).name)

	}
}
 */


fun logger(name: String): KLogger = klogging.logger(name)

inline fun <reified T : Any> T.logger(): Lazy<KLogger> = lazy {
	klogging.logger(loggerName(T::class))
}