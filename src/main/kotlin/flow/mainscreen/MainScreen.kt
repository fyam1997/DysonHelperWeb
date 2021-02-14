package flow.mainscreen

import components.itemDetail
import components.recipeList
import kotlinx.css.*
import model.ItemDetailModel
import model.Recipe
import react.*
import styled.css
import styled.styledDiv
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
            styledDiv {
                css {
                    size = 200.px
                    background = Color.indianRed.value
                }
            }
            styledDiv {
                css {
                    size = 200.px
                    background = Color.lawnGreen.value
                }
            }
        }
    }

    private fun observeData() {
        vm.recipes.collectWithScope {
            setState { recipeList = it }
        }
        vm.focusingItem.collectWithScope {
            setState { itemDetail = it }
        }
    }

    private fun RBuilder.recipeListColumn() {
        styledDiv {
            css {
                display = Display.flex
                flexDirection = FlexDirection.column
                margin(all = generalPadding)
            }
            styledDiv {
                css {
                    fillRemaining()
                    minHeight = 200.px
                    overflow = Overflow.auto
                    padding(generalPadding)
                    defaultBorder()
                }
                recipeList {
                    list = state.recipeList.orEmpty()
                    onItemClick = vm::onItemClick
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

    interface State : RState {
        var recipeList: List<Recipe>?
        var itemDetail: ItemDetailModel?
    }
}