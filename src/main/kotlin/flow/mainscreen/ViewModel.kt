package flow.mainscreen

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import model.Recipe
import utils.JQueryStatic
import utils.getJsonArray

class ViewModel {
    var recipes = MutableStateFlow(emptyList<Recipe>())

    fun initData() {
        GlobalScope.launch {
            recipes.value = JQueryStatic.getJsonArray("data/recipe.json").map {
                Recipe(
                    outputs = mapOf(),
                    inputs = mapOf(),
                    time = it["time"].unsafeCast<Int>(),
                    facility = it["facility"].unsafeCast<String>()
                )
            }
        }
    }
}