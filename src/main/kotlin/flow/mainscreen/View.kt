package flow.mainscreen

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.html.*
import kotlinx.html.dom.append
import model.Trans
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
        vm.trans.collect(::handleRecipeMap)
    }

    private fun initView() {
        document.body?.append?.table {
            tr {
                td { id = "recipeList" }
                td { id = "requirementCalculator" }
                td { id = "activeRecipes" }
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

    private fun handleRecipeMap(map: Map<String, Trans>) = recipeList?.apply {
        innerHTML = ""
        append {
            table("mdc-data-table__table") {
                map.forEach {
                    recipeRow("", it.key, it.value.en, it.value.cn)
                }
            }
        }
    }

    private fun TagConsumer<HTMLElement>.recipeRow(iconSrc: String, id: String, nameEn: String, nameCn: String) {
        //TODO "mdc-data-table__row--selected"
        tr("mdc-data-table__row") {
            td("mdc-data-table__cell") { +iconSrc }
            td("mdc-data-table__cell") { +id }
            td("mdc-data-table__cell") { +nameEn }
            td("mdc-data-table__cell") { +nameCn }
        }
    }
}