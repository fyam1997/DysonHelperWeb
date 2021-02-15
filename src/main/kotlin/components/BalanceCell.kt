package components

import kotlinx.css.*
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import model.Item
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.p
import styled.css
import styled.styledDiv
import styled.styledInput
import utils.inputValue

class BalanceCell : RComponent<BalanceCell.Props, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                flexDirection = FlexDirection.column
            }
            itemGroup {
                items = props.itemBalance?.mapValues { it.value * props.balanceSecond }.orEmpty()
                onItemClick = props.onItemClick
                columnCount = 5
            }
            styledDiv {
                css {
                    display = Display.flex
                    flexDirection = FlexDirection.row
                }
                p { +"每" }
                styledInput {
                    css {
                        width = 64.px
                    }
                    attrs {
                        value = props.balanceSecond.toString()
                        type = InputType.number
                        step = "1"
                        min = "1"
                        onChangeFunction = {
                            props.onBalanceSecondChange(it.inputValue.toFloatOrNull()?.toInt() ?: 0)
                        }
                    }
                }
                p { +"秒" }
            }
        }
    }

    interface Props : RProps {
        var balanceSecond: Int
        var onBalanceSecondChange: (Int) -> Unit
        var itemBalance: Map<Item, kotlin.Float>?
        var onItemClick: (Item) -> Unit
    }
}

fun RBuilder.balanceCell(
    handler: BalanceCell.Props.() -> Unit
) = child(BalanceCell::class) {
    attrs(handler)
}