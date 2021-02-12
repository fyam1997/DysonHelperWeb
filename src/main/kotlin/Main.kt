import kotlinx.browser.document
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.html.FlowOrHeadingContent
import kotlinx.html.div
import kotlinx.html.dom.append
import kotlinx.html.id
import kotlinx.html.link

var map: Map<String, Trans> = emptyMap()
    set(map) {
        field = map
        for ((k, v) in map) {
            mainText += "$k: ${v.cn}, ${v.en}"
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
        map = JQuery.getJSON("data/trans.json").json.toMap(::Trans)
    }
}

data class Trans(
    val cn: String,
    val en: String
) {
    constructor(json: dynamic) : this(json.cn.unsafeCast<String>(), json.en.unsafeCast<String>())
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

fun FlowOrHeadingContent.iconTable() {

}