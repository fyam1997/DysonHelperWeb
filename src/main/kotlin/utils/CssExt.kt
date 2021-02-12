package utils

import kotlinx.css.CSSBuilder

fun css(
    builder: CSSBuilder = CSSBuilder(),
    script: CSSBuilder.() -> Unit
) = builder.apply(script).toString()