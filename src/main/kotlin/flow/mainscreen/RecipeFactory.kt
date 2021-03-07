package flow.mainscreen

import model.Item
import model.Recipe

class RecipeFactory(
    private val iconMap: Map<String, String>
) {
    fun makeRecipe(raw: Recipe.Raw) = with(raw) {
        Recipe(
            outputs = outputs.mapKeys { makeItem(it.key) }.toMap(),
            inputs = inputs.mapKeys { makeItem(it.key) }.toMap(),
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

val Recipe.raw
    get() = Recipe.Raw(
        outputs = outputs.mapKeys { it.key.name },
        inputs = inputs.mapKeys { it.key.name },
        time = time,
        facility = facility.name
    )
