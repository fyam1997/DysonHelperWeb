package utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import react.Component
import react.RState
import react.setState

inline fun <T> Flow<T>.collectWithScope(
    coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main),
    crossinline action: (T) -> Unit
) {
    coroutineScope.launch {
        collect { action(it) }
    }
}

inline fun <T, S : RState> Component<*, S>.collectToState(
    flow: Flow<T>,
    coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main),
    crossinline action: S.(T) -> Unit
) {
    flow.collectWithScope(coroutineScope) {
        setState {
            action(it)
        }
    }
}

fun <T> MutableStateFlow<T>.update(action: (T) -> T): T {
    value = action(value)
    return value
}