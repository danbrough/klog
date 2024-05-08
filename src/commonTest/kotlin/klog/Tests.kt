package klog

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlin.test.Test


class Tests {

	companion object {
		private val log by logger()
		val s = KotlinLogging.logger { }
	}

	@Test
	fun test1() {
		s.info { "s.info" }
		log.trace { "test1() trace" }
		log.debug { "test1() debug" }
		log.info { "test1() info" }
		log.warn { "test1() warn" }
		log.error(Exception("Test Exception")) { "test1() error" }
	}
}