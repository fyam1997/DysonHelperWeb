package components

import kotlinx.css.*
import model.ItemDetailModel
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.b
import react.dom.br
import react.dom.p
import styled.css
import styled.styledDiv
import styled.styledImg
import utils.fillRemaining
import utils.size

class ItemDetail : RComponent<ItemDetail.Props, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                fillRemaining()
                display = Display.flex
                flexDirection = FlexDirection.column
            }
            itemDesc(props.detail)
        }
    }

    private fun RBuilder.itemDesc(detail: ItemDetailModel) {
        styledDiv {
            css {
                display = Display.flex
                flexDirection = FlexDirection.row
                alignItems = Align.center
            }
            styledImg {
                css { size = 32.px }
                attrs {
                    src = detail.item.iconPath
                }
            }
            p {
                b { +detail.item.name }
                if (detail.item.desc.isNotEmpty()) {
                    br {}
                    +detail.item.desc
                }
            }
        }
    }

    interface Props : RProps {
        var detail: ItemDetailModel
    }
}

fun RBuilder.itemDetail(
    builder: ItemDetail.Props.() -> Unit
) = child(ItemDetail::class) {
    attrs(builder)
}