package org.danbrough.klog

interface PropertyResolver<T> {
  operator fun get(name: String): T?
  operator fun set(name: String, value: T)
  fun parentName(name: String): String?
  fun parent(name: String): T?
}

private interface CachedPropertyResolver

fun <T> PropertyResolver<T>.cached(cache: MutableMap<String, T> = mutableMapOf()): PropertyResolver<T> =
  if (this is CachedPropertyResolver) this else object : PropertyResolver<T> by this,
    CachedPropertyResolver {
    override fun get(name: String): T? = cache[name] ?: this@cached[name]?.also {
      cache[name] = it
    }

    override fun set(name: String, value: T) {
      cache[name] = value
      this@cached[name] = value
    }
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

  private fun parent(name: String, originalName: String): T? =
    get(name) ?: parentName(name)?.let { parentName ->
      parent(parentName, originalName)
    }

  override fun parent(name: String): T? = parent(name, name)
}

interface PropertyResolverWithDefaultParent<T> : PropertyResolver<T> {
  fun defaultValue(name: String): T
}

fun <T> PropertyResolver<T>.default(defaultValue: (String, T?) -> T): PropertyResolverWithDefaultParent<T> =
  if (this is PropertyResolverWithDefaultParent<*>) error("$this already has a default")
  else object : PropertyResolver<T> by this, PropertyResolverWithDefaultParent<T> {
    override fun defaultValue(name: String): T =
      this@default[name] ?: defaultValue(name, this@default.parent(name)).also {
        this@default[name] = it
      }
  }


