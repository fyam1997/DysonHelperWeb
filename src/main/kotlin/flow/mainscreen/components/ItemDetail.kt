package flow.mainscreen.components

import kotlinx.css.*
import kotlinx.html.*
import model.ItemDetailModel
import utils.css
import utils.size

fun TagConsumer<*>.itemDetailView(detail: ItemDetailModel) {
    div {
        style = css {
            display = Display.flex
            flexDirection = FlexDirection.column
        }
        itemDesc(detail)
    }
}

fun TagConsumer<*>.itemDesc(detail: ItemDetailModel) {
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