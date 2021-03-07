package flow.mainscreen

import kotlinx.browser.localStorage
import kotlinx.serialization.Serializable
import model.Recipe
import utils.decodeToJson
import utils.encodeToString

class CacheRepository {
    suspend fun putSelectedRecipeMap(map: Map<Recipe.Raw, Int>) {
        val rawString = map.toStorageObject().encodeToString()
        localStorage.setItem("selectedRecipeMap", rawString)
    }

    suspend fun getSelectedRecipeMap(): Map<Recipe.Raw, Int>? {
        val rawString = localStorage.getItem("selectedRecipeMap")
        val list: List<SelectedRecipe>? = rawString?.decodeToJson()
        return list?.map {
            it.recipe to it.number
        }?.toMap()
    }

    //TODO use this class for view logic
    @Serializable
    data class SelectedRecipe(
        val recipe: Recipe.Raw,
        val number: Int
    )

    private fun Map<Recipe.Raw, Int>.toStorageObject(): List<SelectedRecipe> = map {
        SelectedRecipe(
            recipe = it.key,
            number = it.value
        )
    }
}
