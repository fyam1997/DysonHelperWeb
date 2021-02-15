package flow.mainscreen

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import model.Item
import model.ItemDetailModel
import model.Recipe
import utils.*

class ViewModel(
    private val jsonRepo: JsonRepository
) {
    private val originRecipes = MutableStateFlow(emptyList<Recipe>())
    val recipes = MutableStateFlow(emptyList<Recipe>())

    val selectedRecipes = MutableStateFlow(emptyMap<Recipe, Int>())
    val itemBalance = MutableStateFlow(emptyMap<Item, Float>())
    val facilityRequirement = MutableStateFlow(emptyMap<Item, Int>())

    val focusingItem = MutableStateFlow<ItemDetailModel?>(null)

    fun initData() {
        GlobalScope.launch {
            val iconMap = jsonRepo.iconMap()
            val factory = RecipeFactory(iconMap)
            originRecipes.value = jsonRepo.recipes().map(factory::makeRecipe).sortedByDescending { it.facility.name }
            recipes.value = originRecipes.value
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

    fun onFilterTextChange(value: String) {
        recipes.value = originRecipes.value.doIf(value.isNotEmpty()) {
            filter { recipe ->
                with(recipe) {
                    inputs.any { it.key.name.contains(value) } ||
                            outputs.any { it.key.name.contains(value) } ||
                            facility.name.contains(value)
                }
            }
        }
    }

    fun selectRecipeNumber(recipe: Recipe, number: Int) {
        selectedRecipes.update { map ->
            map.copy {
                put(recipe, number)
            }.filter {
                it.value != 0
            }
        }
        updateBalance()
    }

    private fun updateBalance() {
        val newItemBalance = mutableMapOf<Item, Float>()
        val newFacilityRequirement = mutableMapOf<Item, Int>()
        selectedRecipes.value.forEachPair { recipe, recipeNum ->
            recipe.inputs.forEachPair { item, number ->
                newItemBalance.editOrPut(item, 0f) {
                    it - number.toFloat() * recipeNum / recipe.time
                }
            }
            recipe.outputs.forEachPair { item, number ->
                newItemBalance.editOrPut(item, 0f) {
                    it + number.toFloat() * recipeNum / recipe.time
                }
            }
            newFacilityRequirement.editOrPut(recipe.facility, 0) {
                it + recipeNum
            }
        }
        itemBalance.value = newItemBalance
        facilityRequirement.value = newFacilityRequirement
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
            iconPath = "itemIcons/${iconMap[id].orEmpty()}.png"
        )
    }
}