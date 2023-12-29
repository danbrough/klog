package klog.formatting

import klog.Level

val Level.color: Int
  get() = when (this) {
    Level.TRACE -> 35
    Level.DEBUG -> 36
    Level.INFO -> 32
    Level.WARN -> 33
    else -> 31
  }


/*
data class FormattingOptions(
  var colored: Boolean = false,
  override val name: String = SECTION_FORMATTING,
) : LogConfig.Section {
  override fun buildUpon() = FormattingBuilder().apply {
    this.colored = this@FormattingOptions.colored
  }
}

class FormattingBuilder : LogConfigBuilder.SectionBuilder {
  var colored = false

  override fun build(): LogConfig.Section = FormattingOptions(colored)
}


fun LogConfigBuilder.formatting(block: FormattingBuilder.() -> Unit) {
  val section: FormattingBuilder =
    sections.firstOrNull { it is FormattingBuilder } as? FormattingBuilder
      ?: FormattingBuilder().also {
        sections.add(it)
      }
  section.apply(block)
}



*/

