package components

import kotlinx.css.*
import model.Item
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import styled.css
import styled.styledDiv
import utils.fillRemaining
import utils.forEachPair

class ItemGroup : RComponent<ItemGroup.Props, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                height = LinearDimension.auto
                display = Display.grid
                gridTemplateColumns = GridTemplateColumns("auto " * props.columnCount)
                alignItems = Align.center
                justifyContent = JustifyContent.center
                fillRemaining()
            }
            props.items.forEachPair { item, num ->
                itemCell {
                    this.item = item
                    onItemClick = props.onItemClick
                    number = num
                }
            }
        }

    }

    interface Props : RProps {
        var items: Map<Item, Number>
        var onItemClick: (Item) -> Unit
        var columnCount: Int
    }
}

fun RBuilder.itemGroup(
    handler: ItemGroup.Props.() -> Unit
) = child(ItemGroup::class) {
    attrs(handler)
}