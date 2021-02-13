package flow.mainscreen.components

import kotlinx.css.*
import kotlinx.html.*
import kotlinx.html.js.onClickFunction
import model.Item
import model.Recipe
import utils.*

fun TagConsumer<*>.recipeListView(
    list: List<Recipe>,
    onItemClick: (Item) -> Unit
) {
    materialTable {
        list.forEach { recipe ->
            recipeRowView(recipe, onItemClick)
        }
    }
}

fun TagConsumer<*>.recipeRowView(
    recipe: Recipe,
    onItemClick: (Item) -> Unit
) {
    tableRow {
        itemCellView(recipe.outputs, onItemClick)
        tableCell { +"←" }
        itemCellView(recipe.inputs, onItemClick)
        // TODO check language here
        tableCell { +recipe.facility.name }
        tableCell { +recipe.time.toString() }
    }
}

@HtmlTagMarker
private fun TagConsumer<*>.itemCellView(
    items: Map<Item, Int>,
    onItemClick: (Item) -> Unit
) {
    tableCell {
        div {
            style = css {
                height = LinearDimension.auto
                display = Display.grid
                gridTemplateColumns = GridTemplateColumns("auto auto auto")
                alignItems = Align.center
                justifyContent = JustifyContent.center
            }
            items.forEach {
                val item = it.key
                img {
                    style = css { size = 32.px }
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