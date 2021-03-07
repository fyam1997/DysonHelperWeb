package flow.mainscreen

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
            originRecipes.value =
                recipeRepo.getRecipes().map(factory::makeRecipe).sortedByDescending { it.facility.name }
            filteredRecipes.value = originRecipes.value
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
        itemBalance.value = newItemBalance
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

        private fun makeItem(id: String): Item {
            val iconName = iconMap.getOrElse(id) {
                console.log("icon not found: $id")
                ""
            }
            return Item(
                id = id,
                name = id,
                desc = "",
                iconPath = "itemIcons/$iconName.png"
            )
        }
    }
}