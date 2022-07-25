---
title: KLogRegistry
---
//[klog](../../../index.html)/[org.danbrough.klog](../index.html)/[KLogRegistry](index.html)



# KLogRegistry



[common]\
abstract class [KLogRegistry](index.html)



## Constructors


| | |
|---|---|
| [KLogRegistry](-k-log-registry.html) | [common]<br>fun [KLogRegistry](-k-log-registry.html)() |


## Types


| Name | Summary |
|---|---|
| [Companion](-companion/index.html) | [common]<br>object [Companion](-companion/index.html) |


## Functions


| Name | Summary |
|---|---|
| [applyToBranch](apply-to-branch.html) | [common]<br>abstract fun [applyToBranch](apply-to-branch.html)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), toApply: [KLog](../-k-log/index.html).() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)) |
| [get](get.html) | [common]<br>abstract operator fun [get](get.html)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [KLog](../-k-log/index.html)<br>inline fun [get](get.html)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), level: [Level](../-level/index.html)? = null, noinline writer: [KLogWriter](../index.html#1955773663%2FClasslikes%2F1242518872)? = null, noinline messageFormatter: [KMessageFormatter](../index.html#-1565082679%2FClasslikes%2F1242518872)? = null, noinline nameFormatter: [KNameFormatter](../index.html#-737821257%2FClasslikes%2F1242518872)? = null): [KLog](../-k-log/index.html) |
| [getLogs](get-logs.html) | [common]<br>abstract fun [getLogs](get-logs.html)(): [Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)&lt;[KLog](../-k-log/index.html)&gt; |


## Inheritors


| Name |
|---|
| [DefaultLogRegistry](../-default-log-registry/index.html) |

