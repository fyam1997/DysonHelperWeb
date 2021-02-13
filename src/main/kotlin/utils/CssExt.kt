package utils

import kotlinx.css.*
import kotlinx.css.LinearDimension.Companion.none
import kotlinx.css.properties.border

fun css(
    builder: CSSBuilder = CSSBuilder(),
    script: CSSBuilder.() -> Unit
) = builder.apply(script).toString()

var CSSBuilder.size
    get() = if (width != height) none else width
    set(value) {
        width = value
        height = value
    }

fun CSSBuilder.wrapContent() = flex(flexGrow = 0.0, flexShrink = 0.0, flexBasis = FlexBasis.auto)

fun CSSBuilder.fillRemaining() = flex(flexGrow = 1.0, flexShrink = 1.0, flexBasis = FlexBasis.auto)

fun CSSBuilder.defaultBorder() = border(
    width = 2.px,
    style = BorderStyle.solid,
    color = Color.darkGrey,
    borderRadius = 8.px
)

val CSSBuilder.generalPadding get() = 8.px