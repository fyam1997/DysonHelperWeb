object R {
    private var count = 0
    private val id get() = count++.toString()

    val favicon = id
}