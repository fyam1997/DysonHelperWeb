package flow.mainscreen

import R
import flow.mainscreen.components.itemDetailView
import flow.mainscreen.components.recipeListView
import kotlinx.css.*
import kotlinx.html.*
import kotlinx.html.dom.append
import org.w3c.dom.Document
import utils.*

class View constructor(
    private val document: Document,
    private val vm: ViewModel
) {
    init {
        document.title = "Hello"
        changeIcon("favico.png")
        initView()
        vm.initData()
    }

    private fun initView() {
        document.body?.style?.apply { margin = "0px" }
        document.body?.append {
            div {
                style = css {
                    fontFamily = "\"Arial\",\"Microsoft YaHei\",\"黑体\",\"宋体\",sans-serif;"
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
                        itemDetailView(detail)
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

    private fun changeIcon(iconSrc: String) {
        element(R.favicon)?.let {
            document.head?.removeChild(it)
        }
        document.head?.append?.link {
            id = R.favicon
            rel = "shortcut icon"
            type = "data/t-matrix.png/png"
            href = iconSrc
        }
    }
}