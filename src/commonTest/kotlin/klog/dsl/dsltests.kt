package klog.dsl

import kotlin.test.Test

@DslMarker
annotation class HtmlNodeDSLMarker

interface Node {

  @HtmlNodeDSLMarker
  interface Builder<T : Node> {
    fun build(): T
  }
}

interface ParentNode<T : Node> : Node {
  val children: List<Node>

  interface Builder<T : Node> : Node.Builder<T> {
    val children: MutableList<Node.Builder<*>>
  }

  fun buildUpon(): Builder<T> = object : Builder<T> {
    override val children = mutableListOf<Node.Builder<*>>()

    override fun build(): T {
      TODO("Not yet implemented")
    }
  }
}

data class Html(override val children: List<Node> = emptyList()) : ParentNode<Html>

class HtmlBuilder : ParentNode.Builder<Html> {
  override var children: MutableList<Node.Builder<*>> = mutableListOf()
  override fun build() = Html(children.map { it.build() })
}

class HeadBuilder : ParentNode.Builder<Head> {
  override var children: MutableList<Node.Builder<*>> = mutableListOf()
  var age: Int = 0

  override fun build() = Head(children.map { it.build() }, age)
}


data class Head(override val children: List<Node> = emptyList(), val age: Int) : ParentNode<Head>
data class Body(override val children: List<Node> = emptyList(), val fgColor: String) :
  ParentNode<Body>

data class TextNode(val text: String) : Node

class TextBuilder(var text: String = "") : Node.Builder<TextNode> {

  override fun build() = TextNode(text)
}

class BodyBuilder : ParentNode.Builder<Body> {
  override var children: MutableList<Node.Builder<*>> = mutableListOf()

  var fgColor: String = ""

  operator fun String.unaryPlus() {
    children.add(TextBuilder(this))
  }

  override fun build() = Body(children.map { it.build() }, fgColor)
}


@HtmlNodeDSLMarker
fun html(block: HtmlBuilder.() -> Unit = {}) = HtmlBuilder().apply(block).build()

@HtmlNodeDSLMarker
fun HtmlBuilder.head(block: HeadBuilder.() -> Unit = {}) = children.add(HeadBuilder().apply(block))

@HtmlNodeDSLMarker
fun HtmlBuilder.body(block: BodyBuilder.() -> Unit = {}) = children.add(BodyBuilder().apply(block))

data class TitleNode(val title: String) : Node

class TitleBuilder : Node.Builder<TitleNode> {
  var title: String = ""

  override fun build() = TitleNode(title)
}

@HtmlNodeDSLMarker
fun BodyBuilder.title(block: TitleBuilder.() -> Unit = {}) =
  children.add(TitleBuilder().apply(block))


class DSLTests {
  @Test
  fun test() {
    val doc = html {
      head {
        age = 23
      }

      body {
        fgColor = "Yellow"
        +"Hello Stuff"
        title {
          title = "THe Title"
        }
        +"More Stuff"
      }
    }
  }
}

