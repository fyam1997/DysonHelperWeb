import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.append
import kotlinx.html.js.onClickFunction

fun main() {
    document.title = "Hello"
    changeIcon("icon.png")

    document.body?.append?.div {
        button {
            onClickFunction = {
                changeIcon("icon.png")
            }
            +"Dummy Button!"
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