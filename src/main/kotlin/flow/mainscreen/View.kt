package flow.mainscreen

import flow.mainscreen.components.recipeList
import kotlinx.css.*
import kotlinx.html.*
import kotlinx.html.dom.append
import model.Recipe
import org.w3c.dom.Document
import utils.collectWithScope
import utils.css
import utils.size

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
                    id = "requirementCalculator"
                    style = css {
                        size = 200.px
                        background = Color.indianRed.value
                    }
                }
                div {
                    id = "activeRecipes"
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
            }
            div {
                id = "detailView"
                style = css {
                    flex(flexGrow = 0.0, flexShrink = 0.0, flexBasis = FlexBasis.auto)
                    size = 200.px
                    background = Color.aliceBlue.value
                    overflow = Overflow.auto
                }
            }
            recipeListCell()
        }
    }

    private fun TagConsumer<*>.recipeListCell() {
        div {
            id = "recipeList"
            style = css {
                flex(flexGrow = 1.0, flexShrink = 1.0, flexBasis = FlexBasis.auto)
                minHeight = 50.pct
                overflow = Overflow.auto
            }
        }
        vm.recipes.collectWithScope {
            document.getElementById("recipeList")?.apply {
                innerHTML = ""
                append {
                    recipeList(list = it, iconMap = vm.iconMap.value, onItemClick = vm::onItemClick)
                }
            }
        }
    }

    private fun changeIcon(iconSrc: String) {
        document.getElementById("favicon")?.let {
            document.head?.removeChild(it)
        }
        document.head?.append?.link {
            id = "favicon"
            rel = "shortcut icon"
            type = "data/t-matrix.png/png"
            href = iconSrc
        }
    }
}