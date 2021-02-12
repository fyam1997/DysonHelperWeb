package flow.mainscreen

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.html.*
import kotlinx.html.dom.append
import model.Recipe
import org.w3c.dom.Document
import org.w3c.dom.HTMLElement

class View constructor(
    private val document: Document,
    private val vm: ViewModel
) {
    private val recipeList get() = document.getElementById("recipeList")
    private val requirementCalculator get() = document.getElementById("requirementCalculator")
    private val activeRecipes get() = document.getElementById("activeRecipes")

    init {
        document.title = "Hello"
        changeIcon("icon.png")
        initView()
        observeData()
        vm.initData()
    }

    private fun observeData() = GlobalScope.launch(Dispatchers.Main) {
        vm.recipes.collect(::handleRecipeList)
    }

    private fun initView() {
        document.body?.append {
            table("mdc-data-table__table") {
                tr {
                    td { id = "recipeList" }
                    td { id = "requirementCalculator" }
                    td { id = "activeRecipes" }
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
            table("mdc-data-table__table") {
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
        tr("mdc-data-table__row") {
            td("mdc-data-table__cell") { +inputs.toString() }
            td("mdc-data-table__cell") { +outputs.toString() }
            td("mdc-data-table__cell") { +facility }
            td("mdc-data-table__cell") { +time.toString() }
        }
    }
}