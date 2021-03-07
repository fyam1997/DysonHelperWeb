package utils

import kotlinx.coroutines.await
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.w3c.dom.Window

suspend fun Window.fetString(url: String): String {
    val response = fetch(url).await()
    return response.text().await()
}

inline fun <reified T> String.decodeToJson(): T {
    return Json.decodeFromString(this)
}

inline fun <reified T> T.encodeToString() = Json.encodeToString(this)

external fun parseFloat(num: dynamic): dynamic

fun Number.roundTo(decimalPlace: Int): String = parseFloat(asDynamic().toFixed(decimalPlace)).unsafeCast<String>()