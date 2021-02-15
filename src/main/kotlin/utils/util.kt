package utils

fun <T> T.doIf(
    condition: Boolean,
    action: T.() -> T
): T = if (condition) action() else this

fun <K, V> Map<K, V>.copy(action: MutableMap<K, V>.() -> Unit) = toMutableMap().apply(action)

fun <K, V> Map<K, V>.forEachPair(action: (K, V) -> Unit) = forEach { action(it.key, it.value) }

fun <K, V> MutableMap<K, V>.editOrPut(key: K, default: V, action: (V) -> V) = put(key, get(key)?.let(action) ?: default)