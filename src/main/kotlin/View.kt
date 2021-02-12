import kotlinx.html.*
import kotlinx.html.dom.append
import org.w3c.dom.Document

class View constructor(
    private val document: Document,
    private val vm: ViewModel
) {
    val recipeList get() = document.getElementById("mainDiv")

    init {
        document.title = "Hello"
        changeIcon("icon.png")
        initView()
        vm.initData()
    }

    private fun initView() {
        document.body?.append?.table {
            id = "mainDiv"
            tr {
                td {
                    id = "recipeList"
                }
                td {

                }
                td {

                }
            }
        }
    }

    private fun changeIcon(iconSrc: String) {
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

    fun TABLE.recipeRow() {
    }
}