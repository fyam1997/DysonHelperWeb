package flow.mainscreen

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import model.Item
import model.ItemDetailModel
import model.Recipe
import utils.*

class ViewModel(
    private val recipeRepo: RecipeRepository,
    private val cacheRepo: CacheRepository
) {
    private val originRecipes = MutableStateFlow(emptyList<Recipe>())
    val filteredRecipes = MutableStateFlow(emptyList<Recipe>())

    val selectedRecipes = MutableStateFlow(emptyMap<Recipe, Int>())
    val itemBalance = MutableStateFlow(emptyMap<Item, Float>())

    val focusingItem = MutableStateFlow<ItemDetailModel?>(null)

    fun initData() {
        GlobalScope.launch {
            val iconMap = recipeRepo.getIconMap()
            val factory = RecipeFactory(iconMap)

            val selectedCache = cacheRepo.getSelectedRecipeMap()?.mapKeys {
                factory.makeRecipe(it.key)
            } ?: emptyMap()

            val recipes = recipeRepo.getRecipes().map {
                factory.makeRecipe(it)
            }.sortedByDescending { it.facility.name }

            launch(Dispatchers.Main) {
                originRecipes.value = recipes
                filteredRecipes.value = originRecipes.value
                selectedRecipes.value = selectedCache
            }
        }
    }

    fun onItemClick(item: Item) {
        val canBeInputListTemp = mutableListOf<Recipe>()
        val canBeOutputListTemp = mutableListOf<Recipe>()
        for (recipe in originRecipes.value) {
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
        filteredRecipes.value = originRecipes.value.doIf(value.isNotEmpty()) {
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

    fun selectRecipeNumber(recipe: Recipe) {
        selectedRecipes.update { map ->
            map.copy {
                edit(recipe, 0) { it + 1 }
            }.filter {
                it.value != 0
            }
        }
        updateBalance()
    }

    private fun updateBalance() {
        GlobalScope.launch {
            cacheRepo.putSelectedRecipeMap(selectedRecipes.value.mapKeys { it.key.raw })
            val newItemBalance = mutableMapOf<Item, Float>()
            selectedRecipes.value.forEachPair { recipe, recipeNum ->
                recipe.inputs.forEachPair { item, number ->
                    newItemBalance.edit(item, 0f) {
                        it - number.toFloat() * recipeNum / recipe.time
                    }
                }
                recipe.outputs.forEachPair { item, number ->
                    newItemBalance.edit(item, 0f) {
                        it + number.toFloat() * recipeNum / recipe.time
                    }
                }
            }
            launch(Dispatchers.Main) {
                itemBalance.value = newItemBalance
            }
        }
    }
}