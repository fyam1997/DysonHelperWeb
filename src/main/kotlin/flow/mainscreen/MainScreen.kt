package flow.mainscreen

import components.itemDetail
import components.recipeList
import kotlinx.css.*
import kotlinx.html.js.onChangeFunction
import model.Item
import model.ItemDetailModel
import model.Recipe
import org.w3c.dom.HTMLInputElement
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.br
import react.dom.p
import styled.css
import styled.styledDiv
import styled.styledInput
import utils.*

class MainScreen : RComponent<RProps, MainScreen.State>() {
    private val vm = ViewModel(JsonRepository())

    init {
        observeData()
        vm.initData()
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                fontSize = 12.px
                display = Display.flex
                height = 100.vh
                width = 100.vw
            }
            recipeListColumn()
            selectedRecipeColumn()
            p {
                """
                    TODO
                    make empty state for detail and selected recipes
                    locale
                    time unit
                    dark mode
                    migrate to kotlin-multiplatform
                """.trimIndent().split("\n").forEach {
                    +it
                    br {}
                }
            }
        }
    }

    private fun observeData() {
        collectToState(vm.recipes) { recipeList = it }
        collectToState(vm.focusingItem) { itemDetail = it }
        collectToState(vm.selectedRecipes) { selectedRecipes = it }
        collectToState(vm.itemBalance) { itemBalance = it }
        collectToState(vm.facilityRequirement) { facilityRequirement = it }
    }

    private fun RBuilder.recipeListColumn() {
        styledDiv {
            css {
                display = Display.flex
                flexDirection = FlexDirection.column
                margin(all = generalPadding)
            }
            styledInput {
                attrs {
                    onChangeFunction = {
                        vm.onFilterTextChange((it.target as HTMLInputElement).value)
                    }
                }
            }
            styledDiv {
                css {
                    fillRemaining()
                    minHeight = 200.px
                    overflow = Overflow.auto
                    marginTop = generalPadding
                    padding(generalPadding)
                    defaultBorder()
                }
                recipeList {
                    list = state.recipeList.orEmpty()
                    onItemClick = vm::onItemClick
                    onRecipeDoubleClick = {
                        vm.selectRecipeNumber(it)
                    }
                }
            }
            styledDiv {
                css {
                    wrapContent()
                    overflow = Overflow.auto
                    maxHeight = 40.pct
                    marginTop = generalPadding
                    defaultBorder()
                    padding(generalPadding)
                }
                state.itemDetail?.let {
                    itemDetail {
                        detail = it
                        onItemClick = vm::onItemClick
                    }
                }
            }
        }
    }

    private fun RBuilder.selectedRecipeColumn() {
        styledDiv {
            css {
                display = Display.flex
                flexDirection = FlexDirection.column
                margin(all = generalPadding)
            }
            styledDiv {
                css {
                    wrapContent()
                    overflow = Overflow.auto
                    maxHeight = 40.pct
                    marginTop = generalPadding
                    defaultBorder()
                    padding(generalPadding)
                }
                state.selectedRecipes?.let {
                    recipeList {
                        list = it.keys.toList()
                        onItemClick = vm::onItemClick
                        numberMap = it
                        onNumberChange = vm::selectRecipeNumber
                    }
                }
            }
        }
    }

    interface State : RState {
        var recipeList: List<Recipe>?
        var itemDetail: ItemDetailModel?
        var selectedRecipes: Map<Recipe, Int>?
        var itemBalance: Map<Item, kotlin.Float>?
        var facilityRequirement: Map<Item, Int>?
    }
}