package flow.mainscreen

import kotlinx.browser.window
import model.Recipe
import utils.decodeToJson
import utils.fetString

class RecipeRepository {
    suspend fun getIconMap(): Map<String, String> = window.fetString("data/iconMap.json").decodeToJson()
    suspend fun getRecipes(): List<Recipe.Raw> =
        window.fetString("https://gist.githubusercontent.com/fyam1997/6d0cdf62e99031e817fa85ae30acaeed/raw/dysonSphereRecipes.json")
            .decodeToJson()
}