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
    items: Map<String, Int>,
    iconMap: Map<String, String>,
    onItemClick: (String) -> Unit
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
                val name = it.key
                img {
                    style = css { size = 32.px }
                    src = "itemIcons/${iconMap[name]}"
                    alt = name
                    title = name
                    onClickFunction = { onItemClick(name) }
                }
            }
        }
    }
}