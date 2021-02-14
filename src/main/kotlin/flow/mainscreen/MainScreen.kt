package flow.mainscreen

import R
import components.itemDetailView
import components.recipeListView
import kotlinx.browser.document
import kotlinx.css.*
import kotlinx.html.*
import kotlinx.html.dom.append
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import utils.*

class MainScreen(
) : RComponent<RProps, RState>() {
    private val vm = ViewModel(JsonRepository())

    override fun RBuilder.render() {
        initView()
        vm.initData()
    }

    private fun initView() {
        document.body?.style?.apply { margin = "0px" }
        document.body?.append {
            div {
                style = css {
                    fontSize = 12.px
                    display = Display.flex
                    height = 100.vh
                    width = 100.vw
                }
                recipeListColumn()
                div {
                    id = R.requirementCalculator
                    style = css {
                        size = 200.px
                        background = Color.indianRed.value
                    }
                }
                div {
                    id = R.activeRecipes
                    style = css {
                        size = 200.px
                        background = Color.lawnGreen.value
                    }
                }
            }
        }
    }

    private fun TagConsumer<*>.recipeListColumn() {
        div {
            style = css {
                display = Display.flex
                flexDirection = FlexDirection.column
                margin(all = generalPadding)
            }
            div {
                style = css {
                    wrapContent()
                    overflow = Overflow.auto
                    maxHeight = 40.pct
                    defaultBorder()
                    padding(generalPadding)
                }
                itemDetailCell()
            }
            div {
                style = css {
                    fillRemaining()
                    minHeight = 200.px
                    overflow = Overflow.auto
                    marginTop = generalPadding
                    padding(generalPadding)
                    defaultBorder()
                }
                recipeListCell()
            }
        }
    }

    private fun TagConsumer<*>.itemDetailCell() = div {
        style = css {
            display = Display.flex
            flexDirection = FlexDirection.column
        }
        div { id = R.itemDesc }
        vm.focusingItem.collectWithScope { detail ->
            element(R.itemDesc)?.apply {
                innerHTML = ""
                if (detail != null) {
                    append {
                        itemDetailView(detail = detail, onItemClick = vm::onItemClick)
                    }
                }
            }
        }
    }

    private fun DIV.recipeListCell() {
        id = R.recipeList
        vm.recipes.collectWithScope {
            element(R.recipeList)?.apply {
                innerHTML = ""
                append {
                    recipeListView(list = it, onItemClick = vm::onItemClick)
                }
            }
        }
    }
}