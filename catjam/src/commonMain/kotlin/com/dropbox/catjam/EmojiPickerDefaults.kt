package com.dropbox.catjam

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object EmojiPickerDefaults {
    @Composable
    fun emojiPickerColors(
        contentColor: Color = Color(0xffDEDEDD),
        containerColor: Color = Color(0xff141616),
        textFieldContainerColor: Color = Color(0xff333434),
        textFieldContentColor: Color = Color(0xffDEDEDD),
        accentColor: Color = Color(0xff58A6FF)
    ): EmojiPickerColors = RealEmojiPickerColors(
        contentColor,
        containerColor,
        textFieldContainerColor,
        textFieldContentColor,
        accentColor
    )
}

private data class RealEmojiPickerColors(
    override val contentColor: Color,
    override val containerColor: Color,
    override val textFieldContainerColor: Color,
    override val textFieldContentColor: Color,
    override val accentColor: Color,
) : EmojiPickerColors
