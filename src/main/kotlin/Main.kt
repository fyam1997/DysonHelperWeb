import kotlinx.browser.document
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.html.div
import kotlinx.html.dom.append
import kotlinx.html.id
import kotlinx.html.link

var map: Map<String, Trans> = emptyMap()
    set(map) {
        field = map
        for ((k, v) in map) {
            mainText += "\n$k: ${v.cn}, ${v.en}"
        }
    }

var mainText: String?
    get() = document.getElementById("mainDiv")?.textContent
    set(value) {
        document.getElementById("mainDiv")?.textContent = value
    }

fun main() {
    document.title = "Hello"
    changeIcon("icon.png")
    document.body?.append?.div {
        id = "mainDiv"
    }

    GlobalScope.launch {
        map = JQuery.getJsonSuspend("data/trans.json").toMap(::Trans)
    }
}

fun changeIcon(iconSrc: String) {
    document.getElementById("favicon")?.let {
        document.head?.removeChild(it)
    }
    document.head?.append?.link {
        id = "favicon"
        rel = "shortcut icon"
        type = "image/png"
        href = iconSrc
    }
}