package flow.mainscreen

import kotlinx.browser.window
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import model.Item
import model.ItemDetailModel
import model.Recipe
import utils.fetchJson
import utils.fetchJsonArray
import utils.getJson
import utils.toMap

class ViewModel {
    var recipes = MutableStateFlow(emptyList<Recipe>())

    var focusingItem = MutableStateFlow<ItemDetailModel?>(null)

    fun initData() {
        GlobalScope.launch {
            val iconMap = window.fetchJson("data/iconMap.json").toMap<String>()
            fun makeItem(id: String) = Item(
                id = id,
                name = id,
                desc = "",
                iconPath = "itemIcons/${iconMap[id].orEmpty()}"
            )

            recipes.value = window.fetchJsonArray("data/recipe.json").map { json ->
                Recipe(
                    outputs = json.getJson("outputs").toMap<Int>().map {
                        makeItem(it.key) to it.value
                    }.toMap(),
                    inputs = json.getJson("inputs").toMap<Int>().map {
                        makeItem(it.key) to it.value
                    }.toMap(),
                    time = json["time"].unsafeCast<Int>(),
                    facility = makeItem(json["facility"].unsafeCast<String>())
                )
            }
        }
    }

    fun onItemClick(item: Item) {
        val canBeInputListTemp = mutableListOf<Recipe>()
        val canBeOutputListTemp = mutableListOf<Recipe>()
        for (recipe in recipes.value) {
            if (recipe.inputs.keys.contains(item)) {
                canBeInputListTemp += recipe
            }
            if (recipe.outputs.keys.contains(item)) {
                canBeOutputListTemp += recipe
            }
        }
        focusingItem.value = ItemDetailModel(item, canBeInputListTemp, canBeOutputListTemp)
    }

}