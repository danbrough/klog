---
title: KLogImpl
---
//[klog](../../../index.html)/[org.danbrough.klog](../index.html)/[KLogImpl](index.html)



# KLogImpl



[common]\
data class [KLogImpl](index.html)(registry: [KLogRegistry](../-k-log-registry/index.html), val name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), _level: [Level](../-level/index.html), _writer: [KLogWriter](../index.html#1955773663%2FClasslikes%2F1242518872), _messageFormatter: [KMessageFormatter](../index.html#-1565082679%2FClasslikes%2F1242518872), _nameFormatter: [KNameFormatter](../index.html#-737821257%2FClasslikes%2F1242518872)? = null) : [KLog](../-k-log/index.html)



## Constructors


| | |
|---|---|
| [KLogImpl](-k-log-impl.html) | [common]<br>fun [KLogImpl](-k-log-impl.html)(registry: [KLogRegistry](../-k-log-registry/index.html), name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), _level: [Level](../-level/index.html), _writer: [KLogWriter](../index.html#1955773663%2FClasslikes%2F1242518872), _messageFormatter: [KMessageFormatter](../index.html#-1565082679%2FClasslikes%2F1242518872), _nameFormatter: [KNameFormatter](../index.html#-737821257%2FClasslikes%2F1242518872)? = null) |


## Functions


| Name | Summary |
|---|---|
| [copy](copy.html) | [common]<br>open override fun [copy](copy.html)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), level: [Level](../-level/index.html), writer: [KLogWriter](../index.html#1955773663%2FClasslikes%2F1242518872), messageFormatter: [KMessageFormatter](../index.html#-1565082679%2FClasslikes%2F1242518872), nameFormatter: [KNameFormatter](../index.html#-737821257%2FClasslikes%2F1242518872)?): [KLog](../-k-log/index.html) |
| [debug](../-k-log/debug.html) | [common]<br>open fun [debug](../-k-log/debug.html)(msg: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, err: [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)? = null)<br>open override fun [debug](debug.html)(msg: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, err: [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)?, msgProvider: [LogMessageFunction](../index.html#1090281808%2FClasslikes%2F1242518872)?) |
| [error](../-k-log/error.html) | [common]<br>open fun [error](../-k-log/error.html)(msg: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, err: [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)? = null)<br>open override fun [error](error.html)(msg: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, err: [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)?, msgProvider: [LogMessageFunction](../index.html#1090281808%2FClasslikes%2F1242518872)?) |
| [info](../-k-log/info.html) | [common]<br>open fun [info](../-k-log/info.html)(msg: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, err: [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)? = null)<br>open override fun [info](info.html)(msg: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, err: [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)?, msgProvider: [LogMessageFunction](../index.html#1090281808%2FClasslikes%2F1242518872)?) |
| [trace](../-k-log/trace.html) | [common]<br>open fun [trace](../-k-log/trace.html)(msg: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, err: [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)? = null)<br>open override fun [trace](trace.html)(msg: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, err: [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)?, msgProvider: [LogMessageFunction](../index.html#1090281808%2FClasslikes%2F1242518872)?) |
| [warn](../-k-log/warn.html) | [common]<br>open fun [warn](../-k-log/warn.html)(msg: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, err: [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)? = null)<br>open override fun [warn](warn.html)(msg: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, err: [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)?, msgProvider: [LogMessageFunction](../index.html#1090281808%2FClasslikes%2F1242518872)?) |


## Properties


| Name | Summary |
|---|---|
| [displayName](../-k-log/display-name.html) | [common]<br>open val [displayName](../-k-log/display-name.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [isDebugEnabled](../-k-log/is-debug-enabled.html) | [common]<br>open val [isDebugEnabled](../-k-log/is-debug-enabled.html): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isEnabled](../-k-log/is-enabled.html) | [common]<br>open val [isEnabled](../-k-log/is-enabled.html): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isErrorEnabled](../-k-log/is-error-enabled.html) | [common]<br>open val [isErrorEnabled](../-k-log/is-error-enabled.html): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isInfoEnabled](../-k-log/is-info-enabled.html) | [common]<br>open val [isInfoEnabled](../-k-log/is-info-enabled.html): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isTraceEnabled](../-k-log/is-trace-enabled.html) | [common]<br>open val [isTraceEnabled](../-k-log/is-trace-enabled.html): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isWarnEnabled](../-k-log/is-warn-enabled.html) | [common]<br>open val [isWarnEnabled](../-k-log/is-warn-enabled.html): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [level](level.html) | [common]<br>open override var [level](level.html): [Level](../-level/index.html) |
| [messageFormatter](message-formatter.html) | [common]<br>open override var [messageFormatter](message-formatter.html): [KMessageFormatter](../index.html#-1565082679%2FClasslikes%2F1242518872) |
| [name](name.html) | [common]<br>open override val [name](name.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [nameFormatter](name-formatter.html) | [common]<br>open override var [nameFormatter](name-formatter.html): [KNameFormatter](../index.html#-737821257%2FClasslikes%2F1242518872)? |
| [writer](writer.html) | [common]<br>open override var [writer](writer.html): [KLogWriter](../index.html#1955773663%2FClasslikes%2F1242518872) |

