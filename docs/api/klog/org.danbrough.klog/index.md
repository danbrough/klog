---
title: org.danbrough.klog
---
//[klog](../../index.html)/[org.danbrough.klog](index.html)



# Package org.danbrough.klog



## Types


| Name | Summary |
|---|---|
| [DefaultLogRegistry](-default-log-registry/index.html) | [common]<br>open class [DefaultLogRegistry](-default-log-registry/index.html)(level: [Level](-level/index.html) = Level.WARN, formatter: [KMessageFormatter](index.html#-1565082679%2FClasslikes%2F1242518872) = KMessageFormatters.simple, writer: [KLogWriter](index.html#1955773663%2FClasslikes%2F1242518872) = KLogWriters.noop) : [KLogRegistry](-k-log-registry/index.html) |
| [Hex](-hex/index.html) | [common]<br>class [Hex](-hex/index.html) |
| [KLog](-k-log/index.html) | [common]<br>interface [KLog](-k-log/index.html) |
| [KLogImpl](-k-log-impl/index.html) | [common]<br>data class [KLogImpl](-k-log-impl/index.html)(registry: [KLogRegistry](-k-log-registry/index.html), val name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), _level: [Level](-level/index.html), _writer: [KLogWriter](index.html#1955773663%2FClasslikes%2F1242518872), _messageFormatter: [KMessageFormatter](index.html#-1565082679%2FClasslikes%2F1242518872), _nameFormatter: [KNameFormatter](index.html#-737821257%2FClasslikes%2F1242518872)? = null) : [KLog](-k-log/index.html) |
| [KLogRegistry](-k-log-registry/index.html) | [common]<br>abstract class [KLogRegistry](-k-log-registry/index.html) |
| [KLogWriter](index.html#1955773663%2FClasslikes%2F1242518872) | [common]<br>typealias [KLogWriter](index.html#1955773663%2FClasslikes%2F1242518872) = ([String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [Level](-level/index.html), [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)?) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [KLogWriters](-k-log-writers/index.html) | [common]<br>object [KLogWriters](-k-log-writers/index.html) |
| [KMessageFormatter](index.html#-1565082679%2FClasslikes%2F1242518872) | [common]<br>typealias [KMessageFormatter](index.html#-1565082679%2FClasslikes%2F1242518872) = ([String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [Level](-level/index.html), [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)?, [StatementContext](-statement-context/index.html)) -&gt; [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [KMessageFormatters](-k-message-formatters/index.html) | [common]<br>object [KMessageFormatters](-k-message-formatters/index.html) |
| [KNameFormatter](index.html#-737821257%2FClasslikes%2F1242518872) | [common]<br>typealias [KNameFormatter](index.html#-737821257%2FClasslikes%2F1242518872) = ([String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) -&gt; [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [Level](-level/index.html) | [common]<br>enum [Level](-level/index.html) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[Level](-level/index.html)&gt; |
| [LogMessageFunction](index.html#1090281808%2FClasslikes%2F1242518872) | [common]<br>typealias [LogMessageFunction](index.html#1090281808%2FClasslikes%2F1242518872) = () -&gt; [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [PosixKLogRegistry](-posix-k-log-registry/index.html) | [posix]<br>object [PosixKLogRegistry](-posix-k-log-registry/index.html) : DefaultLogRegistry |
| [StatementContext](-statement-context/index.html) | [common]<br>data class [StatementContext](-statement-context/index.html)(val threadName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val threadID: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val line: [StatementContext.LineContext](-statement-context/-line-context/index.html)? = null) |


## Functions


| Name | Summary |
|---|---|
| [createKLogRegistry](create-k-log-registry.html) | [common, android, js, jvm, posix]<br>[common]<br>expect fun [createKLogRegistry](create-k-log-registry.html)(): [KLogRegistry](-k-log-registry/index.html)<br>[android, js, jvm, posix]<br>actual fun [createKLogRegistry](create-k-log-registry.html)(): KLogRegistry |
| [getTimeMillis](get-time-millis.html) | [common, js, jvmCommon, posix]<br>[common]<br>expect fun [getTimeMillis](get-time-millis.html)(): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)<br>[js, jvmCommon, posix]<br>actual fun [getTimeMillis](get-time-millis.html)(): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [klog](klog.html) | [common]<br>inline fun &lt;[T](klog.html) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; [T](klog.html).[klog](klog.html)(): [KLog](-k-log/index.html)<br>inline fun [klog](klog.html)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [KLog](-k-log/index.html)<br>inline fun &lt;[T](klog.html) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; [T](klog.html).[klog](klog.html)(config: [KLog](-k-log/index.html).() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): [KLog](-k-log/index.html)<br>inline fun &lt;[T](klog.html) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; [T](klog.html).[klog](klog.html)(level: [Level](-level/index.html)? = null, noinline writer: [KLogWriter](index.html#1955773663%2FClasslikes%2F1242518872)? = null, noinline messageFormatter: [KMessageFormatter](index.html#-1565082679%2FClasslikes%2F1242518872)? = null, noinline nameFormatter: [KNameFormatter](index.html#-737821257%2FClasslikes%2F1242518872)? = null): [KLog](-k-log/index.html)<br>inline fun [klog](klog.html)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), level: [Level](-level/index.html)? = null, noinline writer: [KLogWriter](index.html#1955773663%2FClasslikes%2F1242518872)? = null, noinline messageFormatter: [KMessageFormatter](index.html#-1565082679%2FClasslikes%2F1242518872)? = null, noinline nameFormatter: [KNameFormatter](index.html#-737821257%2FClasslikes%2F1242518872)? = null): [KLog](-k-log/index.html)<br>inline fun [klog](klog.html)(clazz: [KClass](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html)&lt;*&gt;, level: [Level](-level/index.html)? = null, noinline writer: [KLogWriter](index.html#1955773663%2FClasslikes%2F1242518872)? = null, noinline messageFormatter: [KMessageFormatter](index.html#-1565082679%2FClasslikes%2F1242518872)? = null, noinline nameFormatter: [KNameFormatter](index.html#-737821257%2FClasslikes%2F1242518872)? = null): [KLog](-k-log/index.html) |
| [klogName](klog-name.html) | [common, js, jvmCommon, posix]<br>[common]<br>expect fun [KClass](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html)&lt;*&gt;.[klogName](klog-name.html)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>[js, jvmCommon, posix]<br>actual fun [KClass](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html)&lt;*&gt;.[klogName](klog-name.html)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [platformStatementContext](platform-statement-context.html) | [common, js, jvmCommon, posix]<br>[common]<br>expect fun [platformStatementContext](platform-statement-context.html)(): [StatementContext](-statement-context/index.html)<br>[js]<br>actual fun [platformStatementContext](platform-statement-context.html)(): StatementContext<br>[jvmCommon, posix]<br>actual inline fun [platformStatementContext](platform-statement-context.html)(): StatementContext |


## Properties


| Name | Summary |
|---|---|
| [androidLog](android-log.html) | [android]<br>val [KLogWriters](-k-log-writers/index.html#418323504%2FExtensions%2F-1750007422).[androidLog](android-log.html): KLogWriter |
| [color](color.html) | [common]<br>val [Level](-level/index.html).[color](color.html): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [colored](colored.html) | [common]<br>val [KMessageFormatter](index.html#-1565082679%2FClasslikes%2F1242518872).[colored](colored.html): [KMessageFormatter](index.html#-1565082679%2FClasslikes%2F1242518872) |
| [kLogRegistry](k-log-registry.html) | [common]<br>val [kLogRegistry](k-log-registry.html): [KLogRegistry](-k-log-registry/index.html) |
| [stackDepth](stack-depth.html) | [jvmCommon]<br>val [stackDepth](stack-depth.html): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |

