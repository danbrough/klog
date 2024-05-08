package klog

import kotlin.test.Test


class AndroidTests {

	companion object {

		val log by logger()

		init {
			kloggingAndroid()
		}

	}


	@Test
	fun test1() {

		log.trace { "test1(): trace" }
		log.debug { "test1(): debug" }
		log.info { "test1(): info" }
		log.warn { "test1(): warn" }
		log.error { "test1(): error" }

	}

}