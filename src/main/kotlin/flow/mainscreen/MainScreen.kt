package flow.mainscreen

import components.balanceCell
import components.itemDetail
import components.recipeList
import kotlinx.css.*
import kotlinx.html.DIV
import kotlinx.html.InputType
import kotlinx.html.TD
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
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
    private val vm = ViewModel(RecipeRepository(), CacheRepository())

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
        collectToState(vm.filteredRecipes) { recipeList = it }
        collectToState(vm.focusingItem) { itemDetail = it }
        collectToState(vm.selectedRecipes) { selectedRecipes = it }
        collectToState(vm.itemBalance) { itemBalance = it }
    }

    // content components
    private fun RBuilder.itemSearchBox() = styledInput {
        attrs {
            onChangeFunction = {
                vm.onFilterTextChange((it.target as HTMLInputElement).value)
            }
        }
    }

    private fun RBuilder.recipeListBoard() {
        state.recipeList?.takeIf { it.isNotEmpty() }?.let {
            getRecipeList(list = it)
        } ?: p { +"Loading data" }
    }

    private fun RBuilder.itemDetailBoard() {
        state.itemDetail?.let {
            styledDiv {
                css {
                    fillRemaining()
                    display = Display.flex
                    flexDirection = FlexDirection.column
                }
                itemDetail {
                    detail = it
                }
                // TODO check language here
                p { +"可产出自：" }
                getRecipeList(list = it.asInput)
                p { +"可用于：" }
                getRecipeList(list = it.asOutput)
            }
        } ?: p { +"Please select an item" }
    }

    private fun RBuilder.selectedRecipeListBoard() {
        state.selectedRecipes?.takeIf { it.isNotEmpty() }?.keys?.let {
            getRecipeList(list = it) { recipe ->
                styledInput {
                    css {
                        width = 64.px
                    }
                    attrs {
                        value = state.selectedRecipes?.get(recipe)?.toString() ?: ""
                        type = InputType.number
                        step = "1"
                        onChangeFunction = { event ->
                            val num = event.inputValue.toFloatOrNull()?.toInt() ?: 0
                            vm.selectRecipeNumber(recipe, num)
                        }
                    }
                }
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

    // basic components
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

    private fun RBuilder.getRecipeList(
        list: Collection<Recipe>,
        onItemClick: (Item) -> Unit = vm::onItemClick,
        startingColumn: StyledDOMBuilder<TD>.(Recipe) -> Unit = { selectButton(it) }
    ) {
        recipeList {
            this.list = list
            this.onItemClick = onItemClick
            this.startingColumn = startingColumn
        }
    }

    // TODO use RBuilder
    private fun StyledDOMBuilder<TD>.selectButton(recipe: Recipe) {
        styledDiv {
            css {
                marginLeft = 8.px
                size = 24.px
                defaultHoverable()
                display = Display.flex
                justifyContent = JustifyContent.center
                alignItems = Align.center
            }
            attrs {
                onClickFunction = {
                    vm.selectRecipeNumber(recipe)
                }
            }
            +"+"
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