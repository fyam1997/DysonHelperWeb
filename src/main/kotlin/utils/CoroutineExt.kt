package utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

inline fun <T> Flow<T>.collectWithScope(
    coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main),
    crossinline action: suspend (T) -> Unit
) {
    coroutineScope.launch {
        collect(action)
    }
}

fun <T> MutableStateFlow<T>.update(action: (T) -> T): T {
    value = action(value)
    return value
}