package components

import kotlinx.css.*
import kotlinx.html.HtmlTagMarker
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import kotlinx.html.title
import model.Item
import model.Recipe
import org.w3c.dom.HTMLInputElement
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.tbody
import react.dom.td
import styled.*
import utils.forEachPair
import utils.size

class RecipeList : RComponent<RecipeList.Props, RState>() {
    override fun RBuilder.render() {
        styledTable {
            css {
                borderCollapse = BorderCollapse.collapse
            }
            tbody {
                props.list.forEach { recipe ->
                    recipeRowView(recipe, props.onItemClick, props.onNumberChange)
                }
            }
        }
    }

    private fun RBuilder.recipeRowView(
        recipe: Recipe,
        onItemClick: (Item) -> Unit,
        onNumberChange: ((Item, Int) -> Unit)?
    ) {
        styledTr {
            css {
                borderBottomStyle = BorderStyle.solid
                borderBottomColor = Color.darkGrey
                borderBottomWidth = 1.px
                hover {
                    background = Color.aliceBlue.value
                }
            }
            onNumberChange?.let {
                td {
                    styledInput {
                        css {
                            width = 64.px
                        }
                        attrs {
                            type = InputType.number
                            max = "100"
                            min = "0"
                            onChangeFunction = {
                                (it.target as HTMLInputElement).value
                            }
                        }
                    }
                }
            }
            itemCellView(recipe.outputs, onItemClick, onNumberChange, 2)
            td { +"←" }
            itemCellView(recipe.inputs, onItemClick, onNumberChange, 3)
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

    @HtmlTagMarker
    private fun RBuilder.itemCellView(
        items: Map<Item, Int>,
        onItemClick: (Item) -> Unit,
        onNumberChange: ((Item, Int) -> Unit)?,
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
                    styledDiv {
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
                                onClickFunction = { onItemClick(item) }
                            }
                        }
                        styledP { +num.toString() }
                    }
                }
            }
        }
    }

    interface Props : RProps {
        var list: List<Recipe>
        var onItemClick: (Item) -> Unit
        var onNumberChange: ((Item, Int) -> Unit)?
    }
}

fun RBuilder.recipeList(
    builder: RecipeList.Props.() -> Unit
) = child(RecipeList::class) {
    attrs(builder)
}