package components

import kotlinx.css.BorderCollapse
import kotlinx.css.borderCollapse
import model.Item
import model.Recipe
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.tbody
import styled.css
import styled.styledTable

class RecipeList : RComponent<RecipeList.Props, RState>() {
    override fun RBuilder.render() {
        styledTable {
            css {
                borderCollapse = BorderCollapse.collapse
            }
            tbody {
                props.list.forEach { recipe ->
                    recipeRow {
                        this.recipe = recipe
                        onItemClick = props.onItemClick
                        onRecipeDoubleClick = props.onRecipeDoubleClick
                        number = props.numberMap?.get(recipe)
                        onNumberChange = {
                            props.onNumberChange?.invoke(recipe, it)
                        }
                    }
                }
            }
        }
    }

    interface Props : RProps {
        var list: List<Recipe>
        var onItemClick: (Item) -> Unit
        var numberMap: Map<Recipe, Int>?
        var onNumberChange: ((Recipe, Int) -> Unit)?
        var onRecipeDoubleClick: ((Recipe) -> Unit)?
    }
}

fun RBuilder.recipeList(
    builder: RecipeList.Props.() -> Unit
) = child(RecipeList::class) {
    attrs(builder)
}