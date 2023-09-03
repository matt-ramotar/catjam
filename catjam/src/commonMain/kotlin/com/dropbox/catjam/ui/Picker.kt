package com.dropbox.catjam.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.json.Json
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.Store
import org.mobilenativefoundation.store.store5.StoreBuilder
import org.mobilenativefoundation.store.store5.impl.extensions.get


class AsyncCatjam {
    private val stateFlow: MutableStateFlow<Catjam> = MutableStateFlow(Catjam.Initial)

    val state: StateFlow<Catjam> = stateFlow

    val serializer = Json {
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true
    }

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(serializer)
        }
    }

    private suspend fun getFallbackSet(): Set {
        return CatjamDefaults.set
    }

    fun useLocalSet(set: Set) {
        stateFlow.value = Catjam.Data(set)
    }

    private val store: Store<String, Set> = StoreBuilder.from<String, Set, Set>(
        fetcher = Fetcher.of { url ->
            val result = client.get(url)
            println("RESULT = $result")
            val text = result.bodyAsText()
            val set = serializer.decodeFromString<Set>(text)
            set
        },
    ).build()

    suspend fun loadSet(url: String) = try {
        stateFlow.value = Catjam.Loading

        val set = store.get(url)
        stateFlow.value = Catjam.Data(set)

    } catch (error: Throwable) {
        println("ERROR = $error")
        val fallbackSet = getFallbackSet()
        stateFlow.value = Catjam.Data(fallbackSet)
    }
}




sealed interface Catjam {
    object Initial : Catjam
    object Loading : Catjam
    data class Data(val set: Set) : Catjam
    sealed interface Error : Catjam {
        data class Exception(val error: Throwable) : Error
        data class Message(val error: String) : Error
    }
}


object CatjamDefaults {
    val set = Set(
        id = "default",
        categories = listOf(),
        emojis = mutableMapOf(),
        aliases = mutableMapOf(),
    )
    val data = Catjam.Data(set)
}


enum class PickerSize {
    Small,
    Medium,
    Large,
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Picker(
    modifier: Modifier = Modifier,
    size: PickerSize = PickerSize.Medium,
    containerColor: Color = Color(0xff141616),
    contentColor: Color = Color(0xffDEDEDD),
    textFieldContainerColor: Color = Color(0xff333434),
    textFieldContentColor: Color = Color(0xffDEDEDD),
    accentColor: Color = Color(0xff58A6FF),
    icon: @Composable () -> Unit = { DefaultIcon() },
    placeholder: @Composable () -> Unit = { DefaultPlaceholder(contentColor) },
    custom: Set? = null,
    onSelect: (emoji: Emoji) -> Unit,
) {


    val context = rememberCompositionContext()
    val catjam = remember { AsyncCatjam() }
    val state = catjam.state.collectAsState()

    val serializer = Json {
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true
    }


    val params = getLoadJsonParams("native_1.json")

    LaunchedEffect(Unit) {

        val json = loadJson(params)

        if (json != null) {
            val set = serializer.decodeFromString<Set>(json)
            catjam.useLocalSet(set)
        } else {
            catjam.loadSet("https://raw.githubusercontent.com/missive/emoji-mart/main/packages/emoji-mart-data/sets/1/twitter.json")
        }

    }

    val data = state.value

    if (data is Catjam.Data) {

        var searchInput: String by remember { mutableStateOf("") }
        var category: Category by remember { mutableStateOf(data.set.categories[0]) }
        var selected: Emoji? by remember { mutableStateOf(null) }
        val categories = data.set.categories.toMutableList().apply {

            add(
                Category(
                    "slackmoji",
                    type = CategoryType.Slackmoji,
                    emojis = Slackmoji.values().map { it.name.lowercase() })
            )

            if (custom != null) {
                addAll(custom.categories.map {
                    Category(
                        it.id,
                        emojis = it.emojis,
                        type = CategoryType.Custom
                    )
                })
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier.background(containerColor).let {
                when (size) {
                    PickerSize.Small -> it.then(Modifier.size(200.dp))
                    PickerSize.Medium -> it.then(Modifier.size(400.dp))
                    PickerSize.Large -> it.then(Modifier.fillMaxSize())
                }
            }) {
            LazyRow {


                categories.forEach {
                    item {
                        TextButton(onClick = {
                            category = it
                        }) {
                            Text(it.id, color = if (category == it) accentColor else contentColor)
                        }
                    }
                }
            }

            TextField(
                value = searchInput,
                shape = RoundedCornerShape(12.dp),
                onValueChange = { searchInput = it },
                leadingIcon = { Icon(Icons.Default.Search, "Search") },
                placeholder = { Text("Search", color = textFieldContentColor) },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = textFieldContainerColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    unfocusedLabelColor = Color.Transparent,
                    textColor = textFieldContentColor,
                    cursorColor = accentColor
                ),
                modifier = Modifier.fillMaxWidth()
            )

            val minWidth = when (size) {
                PickerSize.Small -> 40.dp
                PickerSize.Medium -> 50.dp
                PickerSize.Large -> 60.dp
            }

            LazyVerticalGrid(GridCells.Adaptive(minWidth), modifier = Modifier.weight(6f)) {

                val emojis = if (searchInput.isNotBlank()) {


                    val nativeEmojis = data.set.emojis.values.filter {
                        it.name.contains(searchInput) || it.id.contains(
                            searchInput
                        ) || it.keywords.contains(searchInput)
                    }

                    val customEmojis = custom?.emojis?.values?.filter {
                        it.name.contains(searchInput) || it.id.contains(
                            searchInput
                        ) || it.keywords.contains(searchInput)
                    }

                    val slackmojis = Slackmoji.values().filter {
                        it.name.lowercase().contains(searchInput.lowercase())
                    }.map {
                        val name = it.name.lowercase()
                        Emoji(
                            id = name,
                            name = name,
                            keywords = listOf(name),
                            skins = listOf(Skin.Local(it)),
                            version = 1
                        )
                    }

                    nativeEmojis + customEmojis + slackmojis
                } else {
                    category.emojis.map { emojiId ->

                        when (category.type) {
                            CategoryType.Native -> data.set.emojis.values.firstOrNull { it.id == emojiId }
                            CategoryType.Slackmoji -> Slackmoji.values().first {
                                it.name.lowercase() == emojiId
                            }.let { s ->
                                val name = s.name.lowercase()
                                Emoji(
                                    id = name,
                                    name = name,
                                    keywords = listOf(name),
                                    skins = listOf(Skin.Local(s)),
                                    version = 1
                                )
                            }

                            CategoryType.Custom -> custom?.emojis?.values?.firstOrNull { it.id == emojiId }
                        }
                    }
                }

                items(emojis) { emoji ->

                    val style = when (size) {
                        PickerSize.Small -> MaterialTheme.typography.headlineMedium
                        PickerSize.Medium -> MaterialTheme.typography.headlineLarge
                        PickerSize.Large -> MaterialTheme.typography.displaySmall
                    }

                    if (emoji != null) {
                        when (emoji) {


                            is Emoji -> {

                                when (val firstSkin = emoji.skins.first()) {
                                    is Skin.Local -> {
                                        val painter = rememberImagePainter(firstSkin.slackmoji)

                                        Image(
                                            painter,
                                            contentDescription = null,
                                            modifier = Modifier.size(minWidth).clickable {
                                                onSelect(emoji)
                                                selected = emoji

                                            }
                                        )
                                    }

                                    is Skin.Remote -> {
                                        val painter = rememberAsyncImagePainter(firstSkin.url)

                                        Image(
                                            painter,
                                            contentDescription = null,
                                            modifier = Modifier.size(minWidth).clickable {
                                                onSelect(emoji)
                                                selected = emoji

                                            }
                                        )
                                    }

                                    is Skin.Native -> {
                                        Text(
                                            firstSkin.native,
                                            style = style,
                                            modifier = Modifier.clickable {
                                                onSelect(emoji)
                                                selected = emoji
                                            })
                                    }
                                }
                            }
                        }


                    }
                }
            }

            if (selected != null) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.weight(3f),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    val firstSkin = selected!!.skins.first()

                    when (firstSkin) {
                        is Skin.Local -> {
                            val painter = rememberImagePainter(firstSkin.slackmoji)

                            Image(
                                painter,
                                contentDescription = null,
                                modifier = Modifier.size(minWidth).clickable {
                                    onSelect(selected!!)
                                }
                            )
                        }

                        is Skin.Remote -> {
                            val painter = rememberAsyncImagePainter(firstSkin.url)

                            Image(
                                painter,
                                contentDescription = null,
                                modifier = Modifier.size(minWidth).clickable {
                                    onSelect(selected!!)
                                }
                            )
                        }

                        is Skin.Native -> {
                            Text(
                                firstSkin.native,
                                style = MaterialTheme.typography.displaySmall,
                                modifier = Modifier.clickable {
                                    onSelect(selected!!)
                                    selected = selected!!
                                })
                        }
                    }


                    Column {
                        Text(
                            selected!!.name,
                            style = MaterialTheme.typography.titleMedium,
                            color = contentColor
                        )
                        Text(
                            ":${selected!!.id}:",
                            style = MaterialTheme.typography.labelLarge,
                            color = contentColor
                        )

                    }
                }
            } else {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.weight(2f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    icon()
                    placeholder()
                }
            }
        }
    }
}


@Composable
private fun DefaultIcon() {
    Text("☝️", style = MaterialTheme.typography.displaySmall)
}

@Composable
private fun DefaultPlaceholder(color: Color) {
    Text("Pick an emoji...️", style = MaterialTheme.typography.titleLarge, color = color)
}


@Composable
fun Slackmoji(modifier: Modifier = Modifier, emoji: Slackmoji, onClick: () -> Unit = {}) {
    val painter = rememberImagePainter(emoji)

    Image(
        painter,
        contentDescription = null,
        modifier = modifier.clickable { onClick() }
    )
}

@Composable
fun Emoji(modifier: Modifier = Modifier, emoji: Emoji, onClick: () -> Unit = {}) {
    val firstSkin = emoji.skins.first()

    when (firstSkin) {
        is Skin.Local -> {
            val painter = rememberImagePainter(firstSkin.slackmoji)

            Image(
                painter,
                contentDescription = null,
                modifier = modifier.clickable { onClick() }
            )
        }

        is Skin.Remote -> {
            val painter = rememberAsyncImagePainter(firstSkin.url)

            Image(
                painter,
                contentDescription = null,
                modifier = modifier.clickable { onClick() }
            )
        }

        is Skin.Native -> {
            Text(
                firstSkin.native,
                style = MaterialTheme.typography.displaySmall,
                modifier = modifier.clickable { onClick() }
            )
        }
    }

}