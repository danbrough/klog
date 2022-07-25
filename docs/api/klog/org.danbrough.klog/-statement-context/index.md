---
title: StatementContext
---
//[klog](../../../index.html)/[org.danbrough.klog](../index.html)/[StatementContext](index.html)



# StatementContext



[common]\
data class [StatementContext](index.html)(val threadName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val threadID: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val line: [StatementContext.LineContext](-line-context/index.html)? = null)



## Constructors


| | |
|---|---|
| [StatementContext](-statement-context.html) | [common]<br>fun [StatementContext](-statement-context.html)(threadName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), threadID: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, line: [StatementContext.LineContext](-line-context/index.html)? = null) |


## Types


| Name | Summary |
|---|---|
| [LineContext](-line-context/index.html) | [common]<br>data class [LineContext](-line-context/index.html)(val lineNumber: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = -1, val functionName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val className: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val fileName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null) |


## Properties


| Name | Summary |
|---|---|
| [line](line.html) | [common]<br>val [line](line.html): [StatementContext.LineContext](-line-context/index.html)? = null |
| [threadID](thread-i-d.html) | [common]<br>val [threadID](thread-i-d.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [threadName](thread-name.html) | [common]<br>val [threadName](thread-name.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [time](time.html) | [common]<br>val [time](time.html): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |

