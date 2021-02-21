package components

import kotlinx.css.BorderCollapse
import kotlinx.css.borderCollapse
import kotlinx.html.TD
import model.Item
import model.Recipe
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.tbody
import styled.StyledDOMBuilder
import styled.css
import styled.styledTable
import utils.fillRemaining

class RecipeList : RComponent<RecipeList.Props, RState>() {
    override fun RBuilder.render() {
        styledTable {
            css {
                fillRemaining()
                borderCollapse = BorderCollapse.collapse
            }
            tbody {
                props.list.forEach { recipe ->
                    recipeRow {
                        this.recipe = recipe
                        onItemClick = props.onItemClick
                        startingColumn = {
                            props.startingColumn(this, recipe)
                        }
                        selected = recipe == props.focusingRecipe
                        onClick = { props.onRecipeClick(recipe) }
                    }
                }
            }
        }
    }

    interface Props : RProps {
        var onRecipeClick: (Recipe) -> Unit
        var focusingRecipe: Recipe?
        var list: Collection<Recipe>
        var onItemClick: (Item) -> Unit
        var startingColumn: StyledDOMBuilder<TD>.(Recipe) -> Unit
    }
}

fun RBuilder.recipeList(
    builder: RecipeList.Props.() -> Unit
) = child(RecipeList::class) {
    attrs(builder)
}