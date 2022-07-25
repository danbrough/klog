---
title: DefaultLogRegistry
---
//[klog](../../../index.html)/[org.danbrough.klog](../index.html)/[DefaultLogRegistry](index.html)



# DefaultLogRegistry



[common]\
open class [DefaultLogRegistry](index.html)(level: [Level](../-level/index.html) = Level.WARN, formatter: [KMessageFormatter](../index.html#-1565082679%2FClasslikes%2F1242518872) = KMessageFormatters.simple, writer: [KLogWriter](../index.html#1955773663%2FClasslikes%2F1242518872) = KLogWriters.noop) : [KLogRegistry](../-k-log-registry/index.html)



## Constructors


| | |
|---|---|
| [DefaultLogRegistry](-default-log-registry.html) | [common]<br>fun [DefaultLogRegistry](-default-log-registry.html)(level: [Level](../-level/index.html) = Level.WARN, formatter: [KMessageFormatter](../index.html#-1565082679%2FClasslikes%2F1242518872) = KMessageFormatters.simple, writer: [KLogWriter](../index.html#1955773663%2FClasslikes%2F1242518872) = KLogWriters.noop) |


## Functions


| Name | Summary |
|---|---|
| [applyToBranch](apply-to-branch.html) | [common]<br>open override fun [applyToBranch](apply-to-branch.html)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), toApply: [KLog](../-k-log/index.html).() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)) |
| [get](get.html) | [common]<br>open operator override fun [get](get.html)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [KLog](../-k-log/index.html)<br>inline fun [get](../-k-log-registry/get.html)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), level: [Level](../-level/index.html)? = null, noinline writer: [KLogWriter](../index.html#1955773663%2FClasslikes%2F1242518872)? = null, noinline messageFormatter: [KMessageFormatter](../index.html#-1565082679%2FClasslikes%2F1242518872)? = null, noinline nameFormatter: [KNameFormatter](../index.html#-737821257%2FClasslikes%2F1242518872)? = null): [KLog](../-k-log/index.html) |
| [getLogs](get-logs.html) | [common]<br>open override fun [getLogs](get-logs.html)(): [Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)&lt;[KLog](../-k-log/index.html)&gt; |


## Inheritors


| Name |
|---|
| [PosixKLogRegistry](../-posix-k-log-registry/index.html) |

