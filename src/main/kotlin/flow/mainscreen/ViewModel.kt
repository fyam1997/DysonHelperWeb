package flow.mainscreen

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import model.Item
import model.Recipe
import utils.JQueryStatic
import utils.getJson
import utils.getJsonArray
import utils.toMap

class ViewModel {
    var recipes = MutableStateFlow(emptyList<Recipe>())
    var iconMap = MutableStateFlow(emptyMap<String, String>())

    var focusingItem = MutableStateFlow<Item?>(null)
    var canBeInputList = MutableStateFlow(emptyList<Recipe>())
    var canBeOutputList = MutableStateFlow(emptyList<Recipe>())

    fun initData() {
        GlobalScope.launch {
            iconMap.value = JQueryStatic.getJson(url = "data/iconMap.json").toMap<String>()
            recipes.value = JQueryStatic.getJsonArray("data/recipe.json").map { json ->
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
        focusingItem.value = item
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
        canBeInputList.value = canBeInputListTemp
        canBeOutputList.value = canBeOutputListTemp
    }

    private fun makeItem(id: String) =
        Item(id = id, name = id, desc = "", iconPath = "itemIcons/${iconMap.value[id].orEmpty()}")
}