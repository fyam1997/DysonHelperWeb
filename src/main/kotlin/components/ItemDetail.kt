package components

import kotlinx.css.*
import model.Item
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
import utils.size

class ItemDetail : RComponent<ItemDetail.Props, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                display = Display.flex
                flexDirection = FlexDirection.column
            }
            itemDesc(props.detail)
            // TODO check language here
            p { +"可用于：" }
            recipeList {
                list = props.detail.asInput
                onItemClick = props.onItemClick
            }
            p { +"可产出自：" }
            recipeList {
                list = props.detail.asOutput
                onItemClick = props.onItemClick
            }
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
                br {}
                if (detail.item.desc.isNotEmpty())
                    +detail.item.desc
            }
        }
    }

    interface Props : RProps {
        var detail: ItemDetailModel
        var onItemClick: (Item) -> Unit
    }
}

fun RBuilder.itemDetail(
    builder: ItemDetail.Props.() -> Unit
) = child(ItemDetail::class) {
    attrs(builder)
}