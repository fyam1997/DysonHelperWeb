fun main() {
    console.log("Hello, ${greet()}")
}

fun greet() = "world"

data class Book(val title: String,
                val price: String,
                val description: String,
                val url: String,
                val coverUrl: String)

interface BookStoreContract {
    interface View {
        fun showBooks(books: List<Book>) // 1
        fun showLoader() // 2
        fun hideLoader() // 3
    }

    interface Presenter {
        fun attach(view: View) // 4
        fun loadBooks() // 5
    }
}

class BookStorePage(private val presenter: BookStoreContract.Presenter) : BookStoreContract.View {
    override fun showBooks(books: List<Book>) {
    }
    override fun showLoader() {
    }
    override fun hideLoader() {
    }
}

class BookStorePresenter : BookStoreContract.Presenter {
    // 2
    private lateinit var view: BookStoreContract.View
    // 3
    override fun attach(view: BookStoreContract.View) {
        this.view = view
    }
    // 4
    override fun loadBooks() {
    }
}
