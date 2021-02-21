package components

import kotlinx.css.*
import kotlinx.html.TD
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
import utils.fillRemaining
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
                    if (props.selected)
                        backgroundColor = Color.lightSteelBlue
                    else
                        hover {
                            backgroundColor = Color.aliceBlue
                        }
                }
                attrs {
                    onClickFunction = { onClick() }
                }
                styledTd {
                    startingColumn()
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
        var onClick: () -> Unit
        var selected: Boolean
        var recipe: Recipe
        var onItemClick: (Item) -> Unit
        var startingColumn: StyledDOMBuilder<TD>.() -> Unit
    }
}

fun RBuilder.recipeRow(
    builder: RecipeRow.Props.() -> Unit
) = child(RecipeRow::class) {
    attrs(builder)
}