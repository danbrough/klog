---
title: PosixKLogRegistry
---
//[klog](../../../index.html)/[org.danbrough.klog](../index.html)/[PosixKLogRegistry](index.html)



# PosixKLogRegistry



[posix]\
object [PosixKLogRegistry](index.html) : DefaultLogRegistry



## Functions


| Name | Summary |
|---|---|
| [applyToBranch](index.html#-1566197469%2FFunctions%2F-2145192532) | [posix]<br>open override fun [applyToBranch](index.html#-1566197469%2FFunctions%2F-2145192532)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), toApply: KLog.() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)) |
| [get](index.html#-588020236%2FFunctions%2F-2145192532) | [posix]<br>open operator override fun [get](index.html#-588020236%2FFunctions%2F-2145192532)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): KLog<br>inline fun [get](index.html#-303539566%2FFunctions%2F-2145192532)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), level: Level? = null, noinline writer: KLogWriter? = null, noinline messageFormatter: KMessageFormatter? = null, noinline nameFormatter: KNameFormatter? = null): KLog |
| [getLogs](index.html#-336691039%2FFunctions%2F-2145192532) | [posix]<br>open override fun [getLogs](index.html#-336691039%2FFunctions%2F-2145192532)(): [Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)&lt;KLog&gt; |

