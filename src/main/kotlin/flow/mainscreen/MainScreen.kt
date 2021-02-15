package flow.mainscreen

import components.itemDetail
import components.recipeList
import kotlinx.css.*
import kotlinx.html.DIV
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
import styled.StyledDOMBuilder
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
            column { recipeListColumn() }
            column { selectedRecipeColumn() }
            column {
                p {
                    """
                    TODO
                    make empty state for detail and selected recipes
                    locale
                    time unit
                    dark mode
                    migrate to kotlin-multiplatform
                    requirement group
                """.trimIndent().split("\n").forEach {
                        +it
                        br {}
                    }
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
                maxHeight = 300.px
                marginTop = generalPadding
                defaultBorder()
                padding(generalPadding)
            }
            state.itemDetail?.let {
                itemDetail {
                    detail = it
                    onItemClick = vm::onItemClick
                    onRecipeDoubleClick = {
                        vm.selectRecipeNumber(it)
                    }
                }
            }
        }
    }

    private fun RBuilder.selectedRecipeColumn() {
        styledDiv {
            css {
                fillRemaining()
                overflow = Overflow.auto
                marginTop = generalPadding
                padding(generalPadding)
                defaultBorder()
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

    private fun RBuilder.column(block: StyledDOMBuilder<DIV>.() -> Unit) {
        styledDiv {
            css {
                display = Display.flex
                flexDirection = FlexDirection.column
                margin(all = generalPadding)
            }
            block()
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