package com.dropbox.catjam.models

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: String,
    val icon: String? = null,
    val emojis: List<String>,
    val type: CategoryType = CategoryType.Native
)
