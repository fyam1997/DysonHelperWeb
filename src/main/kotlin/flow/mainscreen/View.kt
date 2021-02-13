package flow.mainscreen

import flow.mainscreen.components.recipeList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.css.*
import kotlinx.html.div
import kotlinx.html.dom.append
import kotlinx.html.id
import kotlinx.html.link
import kotlinx.html.style
import model.Recipe
import org.w3c.dom.Document
import utils.css

class View constructor(
    private val document: Document,
    private val vm: ViewModel
) {
    private val recipeList get() = document.getElementById("recipeList")
    private val requirementCalculator get() = document.getElementById("requirementCalculator")
    private val activeRecipes get() = document.getElementById("activeRecipes")

    init {
        document.title = "Hello"
        changeIcon("favico.png")
        initView()
        observeData()
        vm.initData()
    }

    private fun observeData() = GlobalScope.launch(Dispatchers.Main) {
        vm.recipes.collect(::handleRecipeList)
    }

    private fun initView() {
        document.body?.style?.apply { margin = "0px" }
        document.body?.append {
            div {
                style = css { display = Display.flex }
                val columnStyle = css {
                    display = Display.flex
                    height = 100.vh
                    overflow = Overflow.auto
                }
                div {
                    style = columnStyle
                    div { id = "recipeList" }
                }
                div {
                    style = columnStyle
                    div { id = "requirementCalculator" }
                }
                div {
                    style = columnStyle
                    div { id = "activeRecipes" }
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
            type = "image/png"
            href = iconSrc
        }
    }

    private fun handleRecipeList(list: List<Recipe>) = recipeList?.apply {
        innerHTML = ""
        append {
            recipeList(list = list, iconMap = vm.iconMap.value, onItemClick = vm::onItemClick)
        }
    }
}