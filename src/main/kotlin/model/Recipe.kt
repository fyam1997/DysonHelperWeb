package model

data class Recipe(
    val outputs: Map<String, Int>,
    val inputs: Map<String, Int>,
    val time: Int,
    val facility: String
)