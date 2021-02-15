package components

import kotlinx.css.*
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onDoubleClickFunction
import kotlinx.html.title
import model.Item
import model.Recipe
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.td
import styled.*
import utils.fillRemaining
import utils.inputValue
import utils.size

class RecipeRow : RComponent<RecipeRow.Props, RState>() {
    override fun RBuilder.render() {
        with(props) {
            styledTr {
                css {
                    userSelect = UserSelect.none
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
                                        it.inputValue.toFloatOrNull()?.toInt() ?: 0
                                    )
                                }
                            }
                        }
                    }
                }
                td {
                    itemGroup {
                        items = recipe.outputs
                        onItemClick = props.onItemClick
                        columnCount = 3
                    }
                }
                td { +"←" }
                td {
                    itemGroup {
                        items = recipe.inputs
                        onItemClick = props.onItemClick
                        columnCount = 3
                    }
                }
                // TODO check language here
                styledTd {
                    css {
                        fillRemaining()
                        display = Display.flex
                    }
                    styledImg {
                        css {
                            marginLeft = LinearDimension.auto
                            size = 32.px
                        }
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