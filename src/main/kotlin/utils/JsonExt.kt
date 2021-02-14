package utils

import kotlinx.coroutines.await
import org.w3c.dom.Window
import kotlin.js.Json

val jsObject = js("Object")

val Json.keys get() = jsObject.keys(this).unsafeCast<Array<String>>()

fun Json.getJson(key: String) = get(key).unsafeCast<Json>()

fun <T> Json.map(parser: (item: dynamic) -> T) = keys.map { parser(get(it)) }

fun <T> Json.mapList(parser: (item: dynamic) -> T) = this.asDynamic()

fun <T> Json.toMap(parser: (item: dynamic) -> T) = keys.map { it to parser(get(it)) }.toMap()

fun <T> Json.toMap() = keys.map { it to get(it).unsafeCast<T>() }.toMap()

suspend fun Window.fetchJson(url: String): Json {
    val response = fetch(url).await()
    val json = response.json().await()
    return json.unsafeCast<Json>()
}

suspend fun Window.fetchJsonArray(url: String): Array<Json> {
    val response = fetch(url).await()
    val json = response.json().await()
    return json.unsafeCast<Array<Json>>()
}