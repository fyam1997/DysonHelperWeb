package flow.mainscreen

import R
import flow.mainscreen.components.recipeList
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

    private fun HtmlBlockTag.recipeListColumn() {
        div {
            style = css {
                display = Display.flex
                flexDirection = FlexDirection.column
            }
            div {
                style = css {
                    wrapContent()
                    overflow = Overflow.auto
                }
                itemDetailCell()
            }
            div {
                style = css {
                    fillRemaining()
                    minHeight = 200.px
                    overflow = Overflow.auto
                }
                recipeListCell()
            }
        }
    }

    private fun HtmlBlockTag.itemDetailCell() = div {
        style = css {
            display = Display.flex
            flexDirection = FlexDirection.column
        }
        div { id = R.itemDesc }
        div { id = R.canBeInputList }
        div { id = R.canBeOutPutList }
        vm.focusingItem.collectWithScope { item ->
            element(R.itemDesc)?.apply {
                innerHTML = ""
                if (item != null) {
                    append {
                        div {
                            style = css {
                                display = Display.flex
                                flexDirection = FlexDirection.row
                                alignItems = Align.center
                            }
                            img {
                                style = css { size = 32.px }
                                src = item.iconPath
                            }
                            p {
                                b { +item.name }
                                br()
                                if (item.desc.isNotEmpty())
                                    +item.desc
                            }
                        }
                    }
                }
            }
        }
        vm.canBeInputList.collectWithScope { list ->
            element(R.canBeInputList)?.apply {
                innerHTML = ""
                if (list.isNotEmpty()) {

                }
            }
        }
        vm.canBeOutputList.collectWithScope { list ->
            element(R.canBeOutPutList)?.apply {
                innerHTML = ""
                if (list.isNotEmpty()) {

                }
            }
        }
    }

    private fun HtmlBlockTag.recipeListCell() {
        id = R.recipeList
        vm.recipes.collectWithScope {
            element(R.recipeList)?.apply {
                innerHTML = ""
                append {
                    recipeList(list = it, onItemClick = vm::onItemClick)
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