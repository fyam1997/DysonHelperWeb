import io.kvision.jquery.JQueryStatic
import io.kvision.jquery.JQueryXHR
import kotlinx.coroutines.channels.Channel
import kotlin.js.Json

@JsNonModule
@JsModule("jquery")
external val JQuery: JQueryStatic = definedExternally
val jsObject = js("Object")

val Json.keys get() = jsObject.keys(this).unsafeCast<Array<String>>()

fun <T> Json.map(parser: (item: dynamic) -> T) = keys.map { parser(get(it)) }

fun <T> Json.toMap(parser: (item: dynamic) -> T) = keys.map { it to parser(get(it)) }.toMap()

val JQueryXHR.json get() = responseJSON.asDynamic().unsafeCast<Json>()

suspend fun JQueryStatic.getJsonSuspend(url: String): Json {
    val channel = Channel<Json>()
    getJSON(url = url) { _, _, jqXHR ->
        channel.offer(jqXHR.json)
    }
    return channel.receive()
}