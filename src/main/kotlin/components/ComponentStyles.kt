package components

import kotlinx.css.*
import kotlinx.css.properties.border
import styled.StyleSheet
import utils.setFonts

object ComponentStyles : StyleSheet("ComponentStyles") {
    val globalStyle by css {
        body {
            margin(0.px)
            setFonts("STXihei", "华文细黑", "Microsoft YaHei", "微软雅黑")
        }
        "::-webkit-scrollbar-thumb" {
            backgroundColor = Color.darkGrey
            border(
                width = 4.px,
                style = BorderStyle.solid,
                color = Color.transparent,
                borderRadius = 8.px
            )
            backgroundClip = BackgroundClip.paddingBox
        }
        "::-webkit-scrollbar" {
            width = 16.px
        }
    }
}
