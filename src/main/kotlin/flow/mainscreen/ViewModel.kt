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

    fun initData() {
        GlobalScope.launch {
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
}