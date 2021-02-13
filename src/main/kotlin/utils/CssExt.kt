package utils

import kotlinx.css.CSSBuilder
import kotlinx.css.LinearDimension.Companion.none
import kotlinx.css.height
import kotlinx.css.width

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