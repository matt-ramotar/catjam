package xyz.ramotar.catjam.models

import kotlinx.serialization.Serializable

@Serializable
data class EmojiSet(
    val id: String = "CATJAM",
    val categories: List<Category>,
    val emojis: Map<String, Emoji>,
    val aliases: Map<String, String>,
)
