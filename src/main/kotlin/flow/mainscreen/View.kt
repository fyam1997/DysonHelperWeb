package flow.mainscreen

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.css.*
import kotlinx.html.*
import kotlinx.html.dom.append
import kotlinx.html.js.onClickFunction
import model.Recipe
import org.w3c.dom.Document
import utils.*

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
                    width = (100f / 3f).pct
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
            materialTable {
                list.forEach {
                    recipeRow(
                        inputs = it.inputs,
                        outputs = it.outputs,
                        facility = it.facility,
                        time = it.time
                    )
                }
            }
        }
    }

    private fun TagConsumer<*>.recipeRow(
        inputs: Map<String, Int>,
        outputs: Map<String, Int>,
        facility: String,
        time: Int
    ) {
        //TODO "mdc-data-table__row--selected"
        tableRow {
            tableCell {
                style = css { textAlign = TextAlign.center }
                outputs.forEach { itemIcon(it.key) }
            }
            tableCell { +"←" }
            tableCell {
                style = css { textAlign = TextAlign.center }
                inputs.forEach { itemIcon(it.key) }
            }
            tableCell { +facility }
            tableCell { +time.toString() }
        }
    }

    @HtmlTagMarker
    private fun TagConsumer<*>.itemIcon(name: String) {
        img {
            style = css { size = 32.px }
            src = "itemIcons/${vm.iconMap.value[name]}"
            alt = name
            title = name
            onClickFunction = { vm.onItemClick(name) }
        }
    }
}