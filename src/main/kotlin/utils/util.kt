package utils

fun <T> T.doIf(
    condition: Boolean,
    action: T.() -> T
): T = if (condition) action() else this

fun <K, V> Map<K, V>.copy(action: MutableMap<K, V>.() -> Unit) = toMutableMap().apply(action)