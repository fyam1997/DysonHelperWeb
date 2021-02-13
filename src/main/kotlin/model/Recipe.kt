package model

data class Recipe(
    val outputs: Map<Item, Int>,
    val inputs: Map<Item, Int>,
    val time: Int,
    val facility: Item
)