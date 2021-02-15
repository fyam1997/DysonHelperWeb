package components

import kotlinx.css.*
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
                    verticalAlign = VerticalAlign.bottom
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
                styledP { +number.toString() }
            }
        }
    }

    interface Props : RProps {
        var item: Item
        var onItemClick: (Item) -> Unit
        var number: Int
    }
}

fun RBuilder.itemCell(
    handler: ItemCell.Props.() -> Unit
) {
    child(ItemCell::class) {
        attrs(handler)
    }
}