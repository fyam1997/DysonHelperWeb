import kotlinx.browser.document
import kotlinx.html.dom.append
import kotlinx.html.id
import kotlinx.html.link
import utils.element

fun main() {
    document.title = "Hello"
    changeIcon("favico.png")
}

private fun changeIcon(iconSrc: String) {
    element(R.favicon)?.let {
        document.head?.removeChild(it)
    }
    document.head?.append?.link {
        id = R.favicon
        rel = "shortcut icon"
        type = "data/t-matrix.png/png"
        href = iconSrc
    }
}
