package flow.mainscreen

import kotlinx.browser.window
import model.Recipe
import utils.decodeToJson
import utils.fetString

class JsonRepository {
    suspend fun iconMap(): Map<String, String> = window.fetString("data/iconMap.json").decodeToJson()
    suspend fun recipes(): List<Recipe.Raw> = window.fetString("data/recipe.json").decodeToJson()
}