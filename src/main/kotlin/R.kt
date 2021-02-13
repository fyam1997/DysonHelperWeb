object R {
    private var count = 0
    private val id get() = count++.toString()

    val itemDesc = id
    val recipeList = id
    val favicon = id
    val requirementCalculator = id
    val activeRecipes = id
}