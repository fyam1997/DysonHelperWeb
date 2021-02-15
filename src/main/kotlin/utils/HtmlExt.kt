package utils

import kotlinx.browser.document
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event

fun element(id: String) = document.getElementById(id)

val Event.inputValue get() = (target as HTMLInputElement).value