package xyz.ramotar.catjam.models

import kotlinx.serialization.Serializable

@Serializable
enum class CategoryType {
    Native,
    Slackmoji,
    Custom
}