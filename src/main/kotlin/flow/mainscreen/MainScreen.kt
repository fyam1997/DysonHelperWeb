package flow.mainscreen

import components.balanceCell
import components.itemDetail
import components.recipeList
import kotlinx.css.*
import kotlinx.html.DIV
import kotlinx.html.js.onChangeFunction
import model.Item
import model.ItemDetailModel
import model.Recipe
import org.w3c.dom.HTMLInputElement
import react.*
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
            column {
                itemSearchBox()
                contentBoard(
                    marginTop = generalPadding,
                    minHeight = 200.px,
                    fillHRemaining = true
                ) { recipeListBoard() }
                contentBoard(
                    marginTop = generalPadding,
                    maxHeight = 300.px,
                    fillHRemaining = false
                ) { itemDetailBoard() }
            }
            column {
                contentBoard(
                    marginTop = generalPadding,
                    minHeight = 200.px,
                    fillHRemaining = true
                ) { selectedRecipeListBoard() }
                contentBoard(
                    marginTop = generalPadding,
                    maxHeight = 300.px,
                    fillHRemaining = false
                ) { balanceCellBoard() }
            }
            column {
                p {
                    """
                    TODO
                    locale
                    dark mode
                    migrate to kotlin-multiplatform
                    add buildSrc module and global version 
                    requirement group
                    component constructor
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
    }

    private fun RBuilder.itemSearchBox() = styledInput {
        attrs {
            onChangeFunction = {
                vm.onFilterTextChange((it.target as HTMLInputElement).value)
            }
        }
    }

    private fun RBuilder.recipeListBoard() {
        state.recipeList?.takeIf { it.isNotEmpty() }?.let {
            recipeList {
                list = it
                onItemClick = vm::onItemClick
                onRecipeDoubleClick = {
                    vm.selectRecipeNumber(it)
                }
            }
        } ?: p { +"Loading data" }
    }

    private fun RBuilder.itemDetailBoard() {
        state.itemDetail?.let {
            itemDetail {
                detail = it
            }
            // TODO check language here
            p { +"可产出自：" }
            recipeList {
                list = it.asInput
                onItemClick = vm::onItemClick
                onRecipeDoubleClick = vm::selectRecipeNumber
            }
            p { +"可用于：" }
            recipeList {
                list = it.asOutput
                onItemClick = vm::onItemClick
                onRecipeDoubleClick = vm::selectRecipeNumber
            }
        } ?: p { +"Please select an item" }
    }

    private fun RBuilder.selectedRecipeListBoard() {
        state.selectedRecipes?.takeIf { it.isNotEmpty() }?.keys?.let {
            recipeList {
                list = it
                onItemClick = vm::onItemClick
                numberMap = state.selectedRecipes
                onNumberChange = vm::selectRecipeNumber
            }
        } ?: p { +"Please Select Recipes" }
    }

    private fun RBuilder.balanceCellBoard() {
        state.itemBalance?.takeIf { it.isNotEmpty() }?.let {
            balanceCell {
                balanceSecond = state.balanceSecond ?: 1
                itemBalance = it
                onItemClick = vm::onItemClick
                onBalanceSecondChange = {
                    setState { balanceSecond = it }
                }
            }
        } ?: p { +"Please Select Recipes" }
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

    private fun RBuilder.contentBoard(
        fillHRemaining: Boolean = false,
        marginTop: LinearDimension? = null,
        minHeight: LinearDimension? = null,
        maxHeight: LinearDimension? = null,
        block: StyledDOMBuilder<DIV>.() -> Unit
    ) {
        styledDiv {
            css {
                if (fillHRemaining) fillRemaining() else wrapContent()
                overflow = Overflow.auto
                marginTop?.let { this.marginTop = it }
                minHeight?.let { this.minHeight = it }
                maxHeight?.let { this.maxHeight = it }
                padding(generalPadding)
                defaultBorder()
                display = Display.flex
            }
            block()
        }
    }

    interface State : RState {
        var recipeList: List<Recipe>?
        var itemDetail: ItemDetailModel?
        var selectedRecipes: Map<Recipe, Int>?
        var itemBalance: Map<Item, kotlin.Float>?
        var balanceSecond: Int?
    }
}