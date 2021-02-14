package flow.mainscreen

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import model.Item
import model.ItemDetailModel
import model.Recipe

class ViewModel(
    private val jsonRepo: JsonRepository
) {
    var recipes = MutableStateFlow(emptyList<Recipe>())

    var focusingItem = MutableStateFlow<ItemDetailModel?>(null)

    fun initData() {
        GlobalScope.launch {
            val iconMap = jsonRepo.iconMap()
            val factory = RecipeFactory(iconMap)
            recipes.value = jsonRepo.recipes().map(factory::makeRecipe)
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

    class RecipeFactory(
        private val iconMap: Map<String, String>
    ) {
        fun makeRecipe(raw: Recipe.Raw) = with(raw) {
            Recipe(
                outputs = outputs.map {
                    makeItem(it.key) to it.value
                }.toMap(),
                inputs = inputs.map {
                    makeItem(it.key) to it.value
                }.toMap(),
                time = time,
                facility = makeItem(facility)
            )
        }

        private fun makeItem(id: String) = Item(
            id = id,
            name = id,
            desc = "",
            iconPath = "itemIcons/${iconMap[id].orEmpty()}"
        )
    }
}