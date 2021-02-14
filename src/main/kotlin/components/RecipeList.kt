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
import react.dom.td
import styled.*
import utils.size

class RecipeList : RComponent<RecipeList.Props, RState>() {
    override fun RBuilder.render() {
        styledTable {
            css {
                borderCollapse = BorderCollapse.collapse
            }
            props.list.forEach { recipe ->
                recipeRowView(recipe, props.onItemClick)
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
            itemCellView(recipe.outputs, onItemClick)
            td { +"←" }
            itemCellView(recipe.inputs, onItemClick)
            // TODO check language here
            td { +recipe.facility.name }
            td { +recipe.time.toString() }
        }
    }

    @HtmlTagMarker
    private fun RBuilder.itemCellView(
        items: Map<Item, Int>,
        onItemClick: (Item) -> Unit
    ) {
        styledTd {
            styledDiv {
                css {
                    height = LinearDimension.auto
                    display = Display.grid
                    gridTemplateColumns = GridTemplateColumns("auto auto auto")
                    alignItems = Align.center
                    justifyContent = JustifyContent.center
                }
                items.forEach {
                    val item = it.key
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