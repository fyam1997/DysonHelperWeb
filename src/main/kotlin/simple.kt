import io.kvision.jquery.JQueryStatic
import io.kvision.jquery.JQueryXHR
import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.append
import kotlinx.html.js.onClickFunction

@JsNonModule
@JsModule("jquery")
external val JQuery: JQueryStatic = definedExternally

fun main() {
    document.title = "Hello"
    changeIcon("icon.png")

    document.body?.append?.div {
        button {
            onClickFunction = { onClick() }
            +"Dummy Button!"
        }
    }
}

fun onClick() {

    val json: JQueryXHR = JQuery.getJSON("data/trans.json") { data: Any, textStatus: String, jqXHR: JQueryXHR ->
        document.body?.append?.h1 {
            +data.toString()
        }
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

fun FlowOrHeadingContent.iconTable() {

}