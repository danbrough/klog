package org.danbrough.klog

///**
// * Provides the parent property name
// */
//typealias ParentNameResolver = (name: String) -> String?
//
///**
// * Provides the value of a property
// */
//interface PropertyResolver<T> {
//  operator fun get(name: String): T?
//  operator fun set(name: String, value: T)
//
//  var parentName: ParentNameResolver
//  fun parent(name: String): T?
//}
//
//
///**
// * Treats the property name as a [nameDelimiter] delimited string and returns the parent name
// */
//fun defaultParentNameResolver(nameDelimiter: String): ParentNameResolver = { name ->
//  name.lastIndexOf(nameDelimiter).takeIf { it > 0 }?.let {
//    name.substring(0, it)
//  }
//}


/*fun ParentNameResolver.withSuffix(suffix: String): ParentNameResolver = { name ->
  name.lastIndexOf(suffix).takeIf { it > 0 }?.let { i ->
    this(name.substring(0, i))?.let { "$it$suffix" }
  }
}

fun ParentNameResolver.withPrefix(prefix: String): ParentNameResolver = { name ->
  if (!name.startsWith(prefix)) null else "".let { name.substring(prefix.length) }.let { this(it) }
    ?.let { "$prefix$it" }
}*/

/**
 * Provides the parent property name
 */
typealias ParentNameResolver = (name: String) -> String?

/*
fun ParentNameResolver.withSuffix(suffix: String): ParentNameResolver = { name ->
  name.lastIndexOf(suffix).takeIf { it > 0 }?.let { i ->
    this(name.substring(0, i))?.let { "$it$suffix" }
  }
}
*/

/**
 * Provides the value of a property
 */
interface PropertyResolver<T> {
  operator fun get(name: String): T?
}


fun parentNameResolverDelimited(
  delimiter: String
): ParentNameResolver = { name ->
  name.lastIndexOf(delimiter).takeIf { it > 0 }?.let { i ->
    name.substring(0, i).takeIf { it.isNotEmpty() }
  }
}

fun <T> propertyResolver(getter: (String) -> T?): PropertyResolver<T> =
  object : PropertyResolver<T> {
    override fun get(name: String): T? = getter(name)
  }


private interface CachedPropertyResolver

/**
 * Caches the value of the property in a map
 */
fun <T> PropertyResolver<T>.cached(cache: MutableMap<String, T> = mutableMapOf()): PropertyResolver<T> =
  if (this is CachedPropertyResolver) this else object : PropertyResolver<T>,
    CachedPropertyResolver {
    override fun get(name: String): T? = cache[name] ?: this@cached[name]?.also { cache[name] = it }
  }


fun <T> PropertyResolver<T>.defaultToParent(parentNameResolver: ParentNameResolver): PropertyResolver<T> =
  object : PropertyResolver<T> {
    override fun get(name: String): T? {
      return this@defaultToParent[name] ?: parentNameResolver(name)?.let { parentName ->
        this[parentName]
      }
    }
  }
