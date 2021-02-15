package components

import kotlinx.css.*
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onDoubleClickFunction
import kotlinx.html.title
import model.Item
import model.Recipe
import org.w3c.dom.HTMLInputElement
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.td
import styled.*
import utils.forEachPair
import utils.size

class RecipeRow : RComponent<RecipeRow.Props, RState>() {
    override fun RBuilder.render() {
        with(props) {
            styledTr {
                css {
                    borderBottomStyle = BorderStyle.solid
                    borderBottomColor = Color.darkGrey
                    borderBottomWidth = 1.px
                    hover {
                        background = Color.aliceBlue.value
                    }
                }
                attrs {
                    onDoubleClickFunction = {
                        onRecipeDoubleClick?.invoke(props.recipe)
                    }
                }
                number?.let {
                    td {
                        styledInput {
                            css {
                                width = 64.px
                            }
                            attrs {
                                value = number?.toString() ?: ""
                                type = InputType.number
                                step = "1"
                                onChangeFunction = {
                                    onNumberChange?.invoke(
                                        (it.target as HTMLInputElement).value.toFloatOrNull()?.toInt() ?: 0
                                    )
                                }
                            }
                        }
                    }
                }
                itemCellView(recipe.outputs, onItemClick, 2)
                td { +"←" }
                itemCellView(recipe.inputs, onItemClick, 3)
                // TODO check language here
                td {
                    styledImg {
                        css { size = 32.px }
                        attrs {
                            src = recipe.facility.iconPath
                            // TODO check language here
                            alt = recipe.facility.name
                            title = recipe.facility.name
                        }
                    }
                }
                td { +"${recipe.time}s" }
            }
        }
    }

    private fun RBuilder.itemCellView(
        items: Map<Item, Int>,
        onItemClick: (Item) -> Unit,
        columnCount: Int
    ) {
        styledTd {
            styledDiv {
                css {
                    height = LinearDimension.auto
                    display = Display.grid
                    gridTemplateColumns = GridTemplateColumns("auto " * columnCount)
                    alignItems = Align.center
                    justifyContent = JustifyContent.center
                }
                items.forEachPair { item, num ->
                    itemCell {
                        this.item = item
                        this.onItemClick = onItemClick
                        number = num
                    }
                }
            }
        }
    }

    interface Props : RProps {
        var recipe: Recipe
        var onRecipeDoubleClick: ((Recipe) -> Unit)?
        var onItemClick: (Item) -> Unit
        var number: Int?
        var onNumberChange: ((Int) -> Unit)?
    }
}

fun RBuilder.recipeRow(
    builder: RecipeRow.Props.() -> Unit
) = child(RecipeRow::class) {
    attrs(builder)
}