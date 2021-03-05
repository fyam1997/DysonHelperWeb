package model

import kotlinx.serialization.Serializable

data class Recipe(
    val outputs: Map<Item, Float>,
    val inputs: Map<Item, Float>,
    val time: Float,
    val facility: Item
){
    @Serializable
    data class Raw(
        val outputs: Map<String, Float>,
        val inputs: Map<String, Float>,
        val time: Float,
        val facility: String
    )
}