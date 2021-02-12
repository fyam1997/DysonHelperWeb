package utils

import io.kvision.jquery.JQueryStatic
import io.kvision.jquery.JQueryXHR
import kotlinx.coroutines.channels.Channel
import kotlin.js.Json

@JsNonModule
@JsModule("jquery")
external val JQueryStatic: JQueryStatic = definedExternally
val jsObject = js("Object")

val Json.keys get() = jsObject.keys(this).unsafeCast<Array<String>>()

fun <T> Json.map(parser: (item: dynamic) -> T) = keys.map { parser(get(it)) }

fun <T> Json.mapList(parser: (item: dynamic) -> T) = this.asDynamic()

fun <T> Json.toMap(parser: (item: dynamic) -> T) = keys.map { it to parser(get(it)) }.toMap()

val JQueryXHR.json get() = responseJSON.asDynamic().unsafeCast<Json>()

suspend fun JQueryStatic.getJsonArray(url: String): Array<Json> {
    val channel = Channel<Array<Json>>()
    getJSON(url = url) { _, _, jqXHR ->
        channel.offer(JSON.parse(jqXHR.responseText))
    }
    return channel.receive()
}

suspend fun JQueryStatic.getJson(url: String): Json {
    val channel = Channel<Json>()
    getJSON(url = url) { _, _, jqXHR ->
        channel.offer(jqXHR.json)
    }
    return channel.receive()
}

suspend fun JQueryStatic.getJsonText(url: String): String {
    val channel = Channel<String>()
    getJSON(url = url) { _, _, jqXHR ->
        channel.offer(jqXHR.responseText)
    }
    return channel.receive()
}