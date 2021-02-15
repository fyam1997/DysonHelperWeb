package components

import kotlinx.css.*
import kotlinx.css.properties.border
import kotlinx.html.js.onClickFunction
import kotlinx.html.title
import model.Item
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import styled.css
import styled.styledDiv
import styled.styledImg
import styled.styledP
import utils.roundTo
import utils.size

class ItemCell : RComponent<ItemCell.Props, RState>() {
    override fun RBuilder.render() {
        with(props) {
            styledDiv {
                attrs {
                    onClickFunction = { onItemClick(item) }
                }
                css {
                    display = Display.flex
                    alignItems = Align.center
                    padding(1.px)
                    hover {
                        padding(0.px)
                        border(
                            width = 1.px,
                            style = BorderStyle.solid,
                            color = Color.grey,
                            borderRadius = 4.px
                        )
                    }
                }
                styledImg {
                    css { size = 32.px }
                    attrs {
                        src = item.iconPath
                        // TODO check language here
                        alt = item.name
                        title = item.name
                    }
                }
                styledP {
                    +number.roundTo(2)
                }
            }
        }
    }

    interface Props : RProps {
        var item: Item
        var onItemClick: (Item) -> Unit
        var number: Number
    }
}

fun RBuilder.itemCell(
    handler: ItemCell.Props.() -> Unit
) {
    child(ItemCell::class) {
        attrs(handler)
    }
}