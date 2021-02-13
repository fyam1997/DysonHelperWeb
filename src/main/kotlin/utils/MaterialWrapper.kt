package utils

import kotlinx.html.*

@HtmlTagMarker
inline fun TagConsumer<*>.tableRow(
    selected: Boolean = false,
    crossinline block: TR.() -> Unit = {}
) = tr(
    classes = "mdc-data-table__row" + "--selected".takeIfOrEmpty(selected),
    block = block
)

@HtmlTagMarker
inline fun TagConsumer<*>.tableCell(
    crossinline block: TD.() -> Unit = {}
) = td(
    classes = "mdc-data-table__cell",
    block = block
)

@HtmlTagMarker
inline fun TagConsumer<*>.materialTable(
    crossinline block: TABLE.() -> Unit = {}
) = table(
    classes = "mdc-data-table__table",
    block = block
)

