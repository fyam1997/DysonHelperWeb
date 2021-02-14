import flow.mainscreen.JsonRepository
import flow.mainscreen.View
import flow.mainscreen.ViewModel
import kotlinx.browser.document

fun main() {
    View(document, ViewModel(JsonRepository()))
}