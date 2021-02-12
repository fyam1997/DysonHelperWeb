package flow.mainscreen

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import model.Trans
import utils.JQueryStatic
import utils.getJsonSuspend
import utils.toMap

class ViewModel {
    var trans = MutableStateFlow(emptyMap<String, Trans>())

    fun initData() {
        GlobalScope.launch {
            trans.value = JQueryStatic.getJsonSuspend("data/trans.json").toMap(::Trans)
        }
    }
}