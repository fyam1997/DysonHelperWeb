package model

import kotlinx.serialization.Serializable

data class Recipe(
    val outputs: Map<Item, Int>,
    val inputs: Map<Item, Int>,
    val time: Int,
    val facility: Item
){
    @Serializable
    data class Raw(
        val outputs: Map<String, Int>,
        val inputs: Map<String, Int>,
        val time: Int,
        val facility: String
    )
}