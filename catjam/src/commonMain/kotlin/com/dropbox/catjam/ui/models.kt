package com.dropbox.catjam.ui

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonObject

@Serializable
enum class CategoryType {
    Native,
    Slackmoji,
    Custom
}

@Serializable
data class Category(
    val id: String,
    val icon: String? = null,
    val emojis: List<String>,
    val type: CategoryType = CategoryType.Native
)

@Serializable
data class Emoji(
    val id: String,
    val name: String,
    val keywords: List<String>,
    val skins: List<Skin>,
    val emoticons: List<String>? = null,
    val version: Int
)


@Serializable
data class Set(
    val id: String = "CATJAM",
    val categories: List<Category>,
    val emojis: Map<String, Emoji>,
    val aliases: Map<String, String>,
)

@Serializable(SkinSerializer::class)
sealed class Skin {
    @Serializable
    data class Native(
        val unified: String,
        val native: String,
    ) : Skin()

    @Serializable
    data class Remote(val url: String) : Skin()

    @Serializable
    data class Local(val slackmoji: Slackmoji) : Skin()
}

@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = Skin::class)
object SkinSerializer : KSerializer<Skin> {

    private val serializer = Json {
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true
    }

    override fun serialize(encoder: Encoder, value: Skin) {
        when (value) {
            is Skin.Native -> encoder.encodeSerializableValue(Skin.Native.serializer(), value)
            is Skin.Remote -> encoder.encodeSerializableValue(Skin.Remote.serializer(), value)
            is Skin.Local -> encoder.encodeSerializableValue(Skin.Local.serializer(), value)
        }
    }

    override fun deserialize(decoder: Decoder): Skin {
        val jsonObject = serializer.decodeFromJsonElement(
            JsonObject.serializer(),
            (decoder as JsonDecoder).decodeJsonElement()
        )
        return when {
            jsonObject.containsKey("unified") -> serializer.decodeFromJsonElement(
                Skin.Native.serializer(),
                jsonObject
            )

            jsonObject.containsKey("url") -> serializer.decodeFromJsonElement(
                Skin.Remote.serializer(),
                jsonObject
            )

            jsonObject.containsKey("slackmoji") -> serializer.decodeFromJsonElement(
                Skin.Local.serializer(),
                jsonObject
            )

            else -> throw IllegalArgumentException("Cannot determine Skin type from JSON")
        }
    }
}