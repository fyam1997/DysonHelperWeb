package flow.mainscreen

import kotlinx.browser.localStorage
import model.Recipe
import utils.decodeToJson
import utils.encodeToString

class CacheRepository {
    suspend fun putSelectedRecipeMap(map: Map<Recipe.Raw, Int>) {
        localStorage.setItem("selectedRecipeMap", map.encodeToString())
    }

    suspend fun getSelectedRecipeMap(): Map<Recipe.Raw, Int>? {
        return localStorage.getItem("selectedRecipeMap")?.decodeToJson()
    }
}
