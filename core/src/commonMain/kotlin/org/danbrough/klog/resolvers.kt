package org.danbrough.klog

interface PropertyResolver<T> {
  operator fun get(name: String): T?
  operator fun set(name: String, value: T)
  fun parentName(name: String): String?
  fun parent(name: String): T?
}

fun <T> propertyResolver(
  nameDelimiter: String = ".", getter: (String) -> T?
): PropertyResolver<T> = object : PropertyResolver<T> {
  override fun get(name: String): T? = getter(name)

  override fun set(name: String, value: T) {}

  override fun parentName(name: String): String? =
    name.lastIndexOf(nameDelimiter).takeIf { it > 0 }?.let {
      name.substring(0, it)
    }

  private fun parent(name: String, originalName: String): T? {

    return (get(name) ?: parentName(name)?.let { parentName ->
      parent(parentName, originalName)
    }).also {
      println("parent: name:$name originalName:$originalName get=${get(name)}")
    }
  }

  override fun parent(name: String): T? = parent(name, name)
}

private interface CachedPropertyResolver

fun <T> PropertyResolver<T>.cached(cache: MutableMap<String, T> = mutableMapOf()): PropertyResolver<T> =
  if (this is CachedPropertyResolver) this else object : PropertyResolver<T>,
    CachedPropertyResolver {
    override fun get(name: String): T? {
      println("CACHE:get $name cached:${cache[name]}")
      return (cache[name] ?: this@cached[name]?.also {
        cache[name] = it
      }).also { value ->
        println("CACHE:result $name = $value")
      }
    }

    override fun set(name: String, value: T) {
      println("CACHE: set( $name = $value) ")
      cache[name] = value
      // this@cached[name] = value
    }

    override fun parentName(name: String): String? = this@cached.parentName(name)

    override fun parent(name: String): T? = this@cached.parent(name)
  }


fun <T> PropertyResolver<T>.getOrDefaultToParent(
  key: String, defaultValue: (key: String, parent: T?) -> T = { _, parent ->
    parent ?: throw NoSuchElementException("key: $key is not set")
  }
): T? =
  this[key] ?: (parentName(key)?.let { getOrDefaultToParent(it, defaultValue) } ?: defaultValue(
    key, null
  ))?.also {
    this[key] = it
  }


