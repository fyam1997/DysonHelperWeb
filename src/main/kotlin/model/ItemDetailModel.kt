package model

data class ItemDetailModel(
    val item: Item,
    val asOutput: List<Recipe>,
    val asInput: List<Recipe>
)