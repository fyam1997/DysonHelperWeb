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
import styled.css
import styled.styledImg
import styled.styledInput
import styled.styledTr
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
                td {
                    itemGroup(
                        items = recipe.outputs,
                        onItemClick = onItemClick,
                        columnCount = 2
                    )
                }
                td { +"←" }
                td {
                    itemGroup(
                        items = recipe.inputs,
                        onItemClick = onItemClick,
                        columnCount = 2
                    )
                }
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