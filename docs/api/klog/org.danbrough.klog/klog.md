---
title: klog
---
//[klog](../../index.html)/[org.danbrough.klog](index.html)/[klog](klog.html)



# klog



[common]\
inline fun &lt;[T](klog.html) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; [T](klog.html).[klog](klog.html)(): [KLog](-k-log/index.html)

inline fun &lt;[T](klog.html) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; [T](klog.html).[klog](klog.html)(level: [Level](-level/index.html)? = null, noinline writer: [KLogWriter](index.html#1955773663%2FClasslikes%2F1242518872)? = null, noinline messageFormatter: [KMessageFormatter](index.html#-1565082679%2FClasslikes%2F1242518872)? = null, noinline nameFormatter: [KNameFormatter](index.html#-737821257%2FClasslikes%2F1242518872)? = null): [KLog](-k-log/index.html)

inline fun &lt;[T](klog.html) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; [T](klog.html).[klog](klog.html)(config: [KLog](-k-log/index.html).() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): [KLog](-k-log/index.html)

inline fun [klog](klog.html)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), level: [Level](-level/index.html)? = null, noinline writer: [KLogWriter](index.html#1955773663%2FClasslikes%2F1242518872)? = null, noinline messageFormatter: [KMessageFormatter](index.html#-1565082679%2FClasslikes%2F1242518872)? = null, noinline nameFormatter: [KNameFormatter](index.html#-737821257%2FClasslikes%2F1242518872)? = null): [KLog](-k-log/index.html)

inline fun [klog](klog.html)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [KLog](-k-log/index.html)

inline fun [klog](klog.html)(clazz: [KClass](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html)&lt;*&gt;, level: [Level](-level/index.html)? = null, noinline writer: [KLogWriter](index.html#1955773663%2FClasslikes%2F1242518872)? = null, noinline messageFormatter: [KMessageFormatter](index.html#-1565082679%2FClasslikes%2F1242518872)? = null, noinline nameFormatter: [KNameFormatter](index.html#-737821257%2FClasslikes%2F1242518872)? = null): [KLog](-k-log/index.html)




