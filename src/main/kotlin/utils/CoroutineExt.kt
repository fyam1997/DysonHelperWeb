package utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
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