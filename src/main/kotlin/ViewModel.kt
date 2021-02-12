import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ViewModel {
    var trans = MutableStateFlow(emptyMap<String, Trans>())

    fun initData() {
        GlobalScope.launch {
            trans.value = JQuery.getJsonSuspend("data/trans.json").toMap(::Trans)
        }
    }
}