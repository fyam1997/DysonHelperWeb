package utils

fun <T> T.doIf(
    condition: Boolean,
    action: T.() -> T
): T = if (condition) action() else this