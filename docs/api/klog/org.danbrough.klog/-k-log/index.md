---
title: KLog
---
//[klog](../../../index.html)/[org.danbrough.klog](../index.html)/[KLog](index.html)



# KLog



[common]\
interface [KLog](index.html)



## Functions


| Name | Summary |
|---|---|
| [copy](copy.html) | [common]<br>abstract fun [copy](copy.html)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = this.name, level: [Level](../-level/index.html) = this.level, writer: [KLogWriter](../index.html#1955773663%2FClasslikes%2F1242518872) = this.writer, messageFormatter: [KMessageFormatter](../index.html#-1565082679%2FClasslikes%2F1242518872) = this.messageFormatter, nameFormatter: [KNameFormatter](../index.html#-737821257%2FClasslikes%2F1242518872)? = this.nameFormatter): [KLog](index.html) |
| [debug](debug.html) | [common]<br>open fun [debug](debug.html)(msg: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, err: [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)? = null)<br>abstract fun [debug](debug.html)(msg: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, err: [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)? = null, msgProvider: [LogMessageFunction](../index.html#1090281808%2FClasslikes%2F1242518872)? = null) |
| [error](error.html) | [common]<br>open fun [error](error.html)(msg: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, err: [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)? = null)<br>abstract fun [error](error.html)(msg: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, err: [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)? = null, msgProvider: [LogMessageFunction](../index.html#1090281808%2FClasslikes%2F1242518872)? = null) |
| [info](info.html) | [common]<br>open fun [info](info.html)(msg: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, err: [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)? = null)<br>abstract fun [info](info.html)(msg: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, err: [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)? = null, msgProvider: [LogMessageFunction](../index.html#1090281808%2FClasslikes%2F1242518872)? = null) |
| [trace](trace.html) | [common]<br>open fun [trace](trace.html)(msg: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, err: [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)? = null)<br>abstract fun [trace](trace.html)(msg: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, err: [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)? = null, msgProvider: [LogMessageFunction](../index.html#1090281808%2FClasslikes%2F1242518872)? = null) |
| [warn](warn.html) | [common]<br>open fun [warn](warn.html)(msg: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, err: [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)? = null)<br>abstract fun [warn](warn.html)(msg: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, err: [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)? = null, msgProvider: [LogMessageFunction](../index.html#1090281808%2FClasslikes%2F1242518872)? = null) |


## Properties


| Name | Summary |
|---|---|
| [displayName](display-name.html) | [common]<br>open val [displayName](display-name.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [isDebugEnabled](is-debug-enabled.html) | [common]<br>open val [isDebugEnabled](is-debug-enabled.html): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isEnabled](is-enabled.html) | [common]<br>open val [isEnabled](is-enabled.html): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isErrorEnabled](is-error-enabled.html) | [common]<br>open val [isErrorEnabled](is-error-enabled.html): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isInfoEnabled](is-info-enabled.html) | [common]<br>open val [isInfoEnabled](is-info-enabled.html): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isTraceEnabled](is-trace-enabled.html) | [common]<br>open val [isTraceEnabled](is-trace-enabled.html): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isWarnEnabled](is-warn-enabled.html) | [common]<br>open val [isWarnEnabled](is-warn-enabled.html): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [level](level.html) | [common]<br>abstract var [level](level.html): [Level](../-level/index.html) |
| [messageFormatter](message-formatter.html) | [common]<br>abstract var [messageFormatter](message-formatter.html): [KMessageFormatter](../index.html#-1565082679%2FClasslikes%2F1242518872) |
| [name](name.html) | [common]<br>abstract val [name](name.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [nameFormatter](name-formatter.html) | [common]<br>abstract var [nameFormatter](name-formatter.html): [KNameFormatter](../index.html#-737821257%2FClasslikes%2F1242518872)? |
| [writer](writer.html) | [common]<br>abstract var [writer](writer.html): [KLogWriter](../index.html#1955773663%2FClasslikes%2F1242518872) |


## Inheritors


| Name |
|---|
| [KLogImpl](../-k-log-impl/index.html) |

