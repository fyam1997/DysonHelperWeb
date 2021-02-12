import io.kvision.jquery.JQueryStatic
import kotlin.js.Json

@JsNonModule
@JsModule("jquery")
external val JQuery: JQueryStatic = definedExternally
val jsObject = js("Object")

val Json.keys get() = jsObject.keys(this).unsafeCast<Array<String>>()

fun <T> Json.map(parser: (item: dynamic) -> T) = keys.map { parser(get(it)) }

fun <T> Json.toMap(parser: (item: dynamic) -> T) = keys.map { it to parser(get(it)) }.toMap()