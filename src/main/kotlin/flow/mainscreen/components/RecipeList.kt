package flow.mainscreen.components

import kotlinx.css.*
import kotlinx.html.*
import kotlinx.html.js.onClickFunction
import model.Recipe
import utils.*

fun TagConsumer<*>.recipeList(
    list: List<Recipe>,
    iconMap: Map<String, String>,
    onItemClick: (String) -> Unit
) {
    materialTable {
        list.forEach { item ->
            recipeRow(item, iconMap, onItemClick)
        }
    }
}

fun TagConsumer<*>.recipeRow(
    recipe: Recipe,
    iconMap: Map<String, String>,
    onItemClick: (String) -> Unit
) {
    tableRow {
        itemCell(recipe.outputs, iconMap, onItemClick)
        tableCell { +"←" }
        itemCell(recipe.inputs, iconMap, onItemClick)
        tableCell { +recipe.facility }
        tableCell { +recipe.time.toString() }
    }
}

@HtmlTagMarker
private fun TagConsumer<*>.itemCell(
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
                    src = "${item.iconPath}}"
                    // TODO check language here
                    alt = item.nameCN
                    title = item.nameCN
                    onClickFunction = { onItemClick(item) }
                }
            }
        }
    }
}