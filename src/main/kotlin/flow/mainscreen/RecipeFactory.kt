package flow.mainscreen

import model.Item
import model.Recipe

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
