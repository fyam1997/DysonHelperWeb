package components

import kotlinx.css.*
import kotlinx.html.HtmlTagMarker
import kotlinx.html.js.onClickFunction
import kotlinx.html.title
import model.Item
import model.Recipe
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
                    recipeRowView(recipe, props.onItemClick)
                }
            }
        }
    }

    private fun RBuilder.recipeRowView(
        recipe: Recipe,
        onItemClick: (Item) -> Unit
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

    @HtmlTagMarker
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
    }
}

fun RBuilder.recipeList(
    builder: RecipeList.Props.() -> Unit
) = child(RecipeList::class) {
    attrs(builder)
}