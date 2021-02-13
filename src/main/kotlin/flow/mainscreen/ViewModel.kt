package flow.mainscreen

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import model.Recipe
import utils.JQueryStatic
import utils.getJson
import utils.getJsonArray
import utils.toMap

class ViewModel {
    var recipes = MutableStateFlow(emptyList<Recipe>())
    var iconMap = MutableStateFlow(emptyMap<String, String>())

    fun initData() {
        GlobalScope.launch {
            iconMap.value = JQueryStatic.getJson(url = "data/iconMap.json").toMap<String>()
            recipes.value = JQueryStatic.getJsonArray("data/recipe.json").map { json ->
                Recipe(
                    outputs = json.getJson("outputs").toMap<Int>(),
                    inputs = json.getJson("inputs").toMap<Int>(),
                    time = json["time"].unsafeCast<Int>(),
                    facility = json["facility"].unsafeCast<String>()
                )
            }
        }
    }

    fun onItemClick(name: String) {
        TODO("Not yet implemented")
    }
}