import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ViewModel {
    var map = MutableStateFlow(emptyMap<String, Trans>())

    fun initData() {
        GlobalScope.launch {
            map.value = JQuery.getJsonSuspend("data/trans.json").toMap(::Trans)
        }
    }
}