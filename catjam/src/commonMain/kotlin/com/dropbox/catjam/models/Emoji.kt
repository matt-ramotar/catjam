package com.dropbox.catjam.models

import kotlinx.serialization.Serializable

@Serializable
data class Emoji(
    val id: String,
    val name: String,
    val keywords: List<String>,
    val skins: List<Skin>,
    val emoticons: List<String>? = null,
    val version: Int
)
