package model

data class Trans(
    val cn: String,
    val en: String
) {
    constructor(json: dynamic) : this(json.cn.unsafeCast<String>(), json.en.unsafeCast<String>())
}