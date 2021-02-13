package flow.mainscreen.components

import kotlinx.css.*
import kotlinx.html.*
import model.Item
import model.ItemDetailModel
import utils.css
import utils.size

fun TagConsumer<*>.itemDetailView(
    detail: ItemDetailModel,
    onItemClick: (Item) -> Unit
) {
    div {
        style = css {
            display = Display.flex
            flexDirection = FlexDirection.column
        }
        itemDesc(detail)
        // TODO check language here
        p { +"可用于：" }
        recipeListView(detail.asInput, onItemClick)
        p { +"可产出自：" }
        recipeListView(detail.asOutput, onItemClick)
    }
}

private fun TagConsumer<*>.itemDesc(detail: ItemDetailModel) {
    div {
        style = css {
            display = Display.flex
            flexDirection = FlexDirection.row
            alignItems = Align.center
        }
        img {
            style = css { size = 32.px }
            src = detail.item.iconPath
        }
        p {
            b { +detail.item.name }
            br()
            if (detail.item.desc.isNotEmpty())
                +detail.item.desc
        }
    }
}