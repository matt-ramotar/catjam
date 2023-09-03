package com.dropbox.catjam.models

import kotlinx.serialization.Serializable

@Serializable
enum class CategoryType {
    Native,
    Slackmoji,
    Custom
}