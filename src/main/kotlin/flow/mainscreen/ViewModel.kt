package flow.mainscreen

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
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

    // TODO use StateFlow.mapIndexed
    private var selectedRecipesCount = 0
    val itemBalance = selectedRecipes.map { map ->
        if (selectedRecipesCount++ != 0)
            cacheRepo.putSelectedRecipeMap(map.mapKeys { it.key.raw })

        val newItemBalance = mutableMapOf<Item, Float>()
        map.forEachPair { recipe, recipeNum ->
            recipe.inputs.forEachPair { item, number ->
                newItemBalance.edit(item, 0f) {
                    it - number * recipeNum / recipe.time
                }
            }
            recipe.outputs.forEachPair { item, number ->
                newItemBalance.edit(item, 0f) {
                    it + number * recipeNum / recipe.time
                }
            }
        }
        newItemBalance
    }

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
    }

    fun selectRecipeNumber(recipe: Recipe) {
        selectedRecipes.update { map ->
            map.copy {
                edit(recipe, 0) { it + 1 }
            }.filter {
                it.value != 0
            }
        }
    }
}