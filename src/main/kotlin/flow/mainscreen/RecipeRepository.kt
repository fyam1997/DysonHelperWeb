package flow.mainscreen

import kotlinx.browser.window
import model.Recipe
import utils.decodeToJson
import utils.fetString

class RecipeRepository {
    suspend fun getIconMap(): Map<String, String> =
        window.fetString("$BaseUrl/iconMap.json")
            .decodeToJson()

    suspend fun getRecipes(): List<Recipe.Raw> =
        window.fetString("$BaseUrl/dysonSphereRecipes.json")
            .decodeToJson()

    companion object{
        const val BaseUrl = "https://gist.githubusercontent.com/fyam1997/6d0cdf62e99031e817fa85ae30acaeed/raw"
    }
}