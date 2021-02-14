package components

import kotlinx.css.*
import styled.StyleSheet
import utils.setFonts

object ComponentStyles : StyleSheet("ComponentStyles") {
    val globalStyle by css {
        body {
            margin(0.px)
            setFonts("STXihei", "华文细黑", "Microsoft YaHei", "微软雅黑")
        }
    }
}