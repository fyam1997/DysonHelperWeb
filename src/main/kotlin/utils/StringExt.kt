package utils

fun String.takeIfOrEmpty(condition: Boolean) = takeIf { condition }.orEmpty()