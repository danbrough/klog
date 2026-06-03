package org.danbrough.klog

interface PropertyResolver<T> {
  val nameDelimiter: String

  operator fun get(name: String): T?

  fun resolve(name: String): T? {
    //println("resolve: $name")
    get(name)?.also {
      //println("got value: $it")
      return it
    }

    val i = name.lastIndexOf(nameDelimiter)
    return if (i > -1) resolve(name.substring(0, i))
    else null
  }

  fun cached(): MutablePropertyResolver<T> = CachedPropertyResolver(this)

  fun <U> map(map: (T) -> U): PropertyResolver<U> = MappedPropertyResolver(this, map)
}

fun <T> propertyResolver(nameDelimiter: String = ".", getter: (String) -> T?): PropertyResolver<T> =
  object : PropertyResolver<T> {
    override val nameDelimiter: String = nameDelimiter

    override fun get(name: String): T? = getter(name)

    override fun resolve(name: String): T? {
      getter(name)?.also {
        return it
      }
      val i = name.lastIndexOf(nameDelimiter)
      return if (i > -1) resolve(name.substring(0, i))
      else null
    }
  }

interface MutablePropertyResolver<T> : PropertyResolver<T> {
  operator fun set(name: String, value: T)
}

private class MappedPropertyResolver<T, U>(
  private val resolver: PropertyResolver<T>, private val map: (T) -> U
) : PropertyResolver<U> {
  override val nameDelimiter: String = resolver.nameDelimiter
  override fun get(name: String): U? = resolver[name]?.let(map)
  override fun resolve(name: String): U? = resolver.resolve(name)?.let(map)
}

private class CachedPropertyResolver<T>(private val resolver: PropertyResolver<T>) :
  MutablePropertyResolver<T> {
  private val cache = mutableMapOf<String, T>()
  override val nameDelimiter: String = resolver.nameDelimiter
  override fun get(name: String): T? = resolver[name]?.also { value ->
    cache[name] = value
  }

  override fun set(name: String, value: T) {
    cache[name] = value
  }

  override fun resolve(name: String): T? = get(name) ?: resolver.resolve(name)?.also { value ->
    cache[name] = value
  }
}




