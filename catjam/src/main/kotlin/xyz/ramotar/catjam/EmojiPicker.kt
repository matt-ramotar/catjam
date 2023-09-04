package xyz.ramotar.catjam

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.json.Json
import xyz.ramotar.catjam.models.Category
import xyz.ramotar.catjam.models.CategoryType
import xyz.ramotar.catjam.models.Emoji
import xyz.ramotar.catjam.models.EmojiSet
import xyz.ramotar.catjam.models.Skin
import xyz.ramotar.catjam.models.Slackmoji


@Composable
fun EmojiPicker(
    modifier: Modifier = Modifier,
    size: EmojiPickerSize = EmojiPickerSize.Medium,
    colors: EmojiPickerColors = EmojiPickerDefaults.emojiPickerColors(),
    icon: @Composable () -> Unit = { DefaultIcon() },
    placeholder: @Composable () -> Unit = { DefaultPlaceholder(colors.contentColor) },
    customEmojis: EmojiSet? = null,
    onSelect: (emoji: Emoji) -> Unit
) {
    val state = emojiSetState()

    EmojiPicker(
        modifier = modifier,
        state = state,
        size = size,
        colors = colors,
        onSelect = onSelect,
        icon = icon,
        placeholder = placeholder,
        customEmojis = customEmojis
    )
}

@Composable
internal fun mapEmojis(): Map<String, Emoji> {
    val nativeEmojis = emojiSetState().value!!.emojis
    val slackmojis = Slackmoji.values().associate {
        val name = it.name.lowercase()
        name to Emoji(
            id = name,
            name = name,
            keywords = listOf(name),
            skins = listOf(Skin.Local(it)),
            version = 1
        )
    }

    return nativeEmojis + slackmojis
}

@Composable
private fun emojiSetState(): State<EmojiSet?> {
    val state = MutableStateFlow<EmojiSet?>(null)

    val serializer = Json {
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true
    }

    val params = getParamsFor("native.json")

    LaunchedEffect(Unit) {
        val json = readJson(params)
        val nativeEmojis = serializer.decodeFromString<EmojiSet>(json)
        state.value = nativeEmojis
    }

    return state.collectAsState()
}

@Composable
private fun EmojiPicker(
    modifier: Modifier,
    state: State<EmojiSet?>,
    size: EmojiPickerSize,
    colors: EmojiPickerColors,
    customEmojis: EmojiSet?,
    icon: @Composable () -> Unit,
    placeholder: @Composable () -> Unit,
    onSelect: (emoji: Emoji) -> Unit,
) {
    val nativeEmojis = state.value

    if (nativeEmojis != null) {

        var searchInput: String by remember { mutableStateOf("") }
        var category: Category by remember { mutableStateOf(nativeEmojis.categories[0]) }
        var selected: Emoji? by remember { mutableStateOf(null) }

        val categories = joinCategories(nativeEmojis, customEmojis)
        val minWidth = getMinWidth(size)


        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier
                .background(colors.containerColor)
                .adjustSize(size)
        ) {
            CategoriesRow(
                categories = categories,
                currentCategory = category,
                accentColor = colors.accentColor,
                contentColor = colors.contentColor
            ) {
                category = it
            }

            SearchTextField(
                value = searchInput,
                containerColor = colors.textFieldContainerColor,
                textColor = colors.textFieldContentColor,
                cursorColor = colors.accentColor,
                onValueChange = { searchInput = it })

            EmojisGrid(
                modifier = Modifier.weight(6f),
                nativeEmojis = nativeEmojis,
                searchInput = searchInput,
                size = size,
                customEmojis = customEmojis,
                category = category,
                minWidth = minWidth,
                onSelect = {
                    onSelect(it)
                    selected = it
                }
            )

            Footer(
                modifier = Modifier.weight(2f),
                selected = selected,
                minWidth = minWidth,
                contentColor = colors.contentColor,
                icon = icon,
                placeholder = placeholder
            )
        }
    }
}

@Composable
private fun CategoriesRow(
    modifier: Modifier = Modifier,
    categories: List<Category>,
    currentCategory: Category,
    accentColor: Color,
    contentColor: Color,
    onSelect: (Category) -> Unit,
) {
    LazyRow(modifier.padding(12.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        categories.forEach {
            item {
                CategoryIcon(
                    modifier = Modifier.clickable { onSelect(it) },
                    category = it,
                    isSelected = currentCategory == it,
                    selectedColor = accentColor,
                    unselectedColor = contentColor
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchTextField(
    modifier: Modifier = Modifier,
    value: String,
    containerColor: Color,
    textColor: Color,
    cursorColor: Color,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        shape = RoundedCornerShape(12.dp),
        onValueChange = onValueChange,
        leadingIcon = { Icon(Icons.Default.Search, "Search") },
        placeholder = { Text("Search", color = textColor) },

        colors = TextFieldDefaults.colors(
            focusedTextColor = textColor,
            unfocusedTextColor = textColor,
            focusedContainerColor = containerColor,
            unfocusedContainerColor = containerColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            unfocusedLabelColor = Color.Transparent,
            cursorColor = cursorColor
        ),
        modifier = modifier.fillMaxWidth()
    )
}


private fun getMatchingEmojis(
    searchInput: String,
    nativeEmojis: EmojiSet,
    customEmojis: EmojiSet?,
): List<Emoji> {

    val matchingNativeEmojis = nativeEmojis.emojis.values.filter {
        it.name.contains(searchInput) || it.id.contains(
            searchInput
        ) || it.keywords.contains(searchInput)
    }

    val matchingCustomEmojis = customEmojis?.emojis?.values?.filter {
        it.name.contains(searchInput) || it.id.contains(
            searchInput
        ) || it.keywords.contains(searchInput)
    }

    val matchingSlackmojis = Slackmoji.values().filter {
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

    return mutableListOf<Emoji>().apply {
        addAll(matchingNativeEmojis)
        addAll(matchingSlackmojis)
        if (matchingCustomEmojis != null) {
            addAll(matchingCustomEmojis)
        }
    }
}

private fun getEmojisForCategory(
    category: Category,
    nativeEmojis: EmojiSet,
    customEmojis: EmojiSet?
): List<Emoji> {
    return category.emojis.mapNotNull { emojiId ->
        when (category.type) {
            CategoryType.Native -> nativeEmojis.emojis.values.firstOrNull { it.id == emojiId }

            CategoryType.Slackmoji -> Slackmoji.values().first { slackmoji ->
                slackmoji.name.lowercase() == emojiId
            }.let {
                val name = it.name.lowercase()
                Emoji(
                    id = name,
                    name = name,
                    keywords = listOf(name),
                    skins = listOf(Skin.Local(it)),
                    version = 1
                )
            }

            CategoryType.Custom -> customEmojis?.emojis?.values?.firstOrNull { it.id == emojiId }
        }
    }
}

@Composable
private fun EmojisGrid(
    modifier: Modifier,
    nativeEmojis: EmojiSet,
    searchInput: String,
    size: EmojiPickerSize,
    customEmojis: EmojiSet?,
    category: Category,
    minWidth: Dp,
    onSelect: (emoji: Emoji) -> Unit,
) {
    LazyVerticalGrid(GridCells.Adaptive(minWidth), modifier = modifier) {

        val emojis = if (searchInput.isNotBlank()) {
            getMatchingEmojis(searchInput, nativeEmojis, customEmojis)
        } else {
            getEmojisForCategory(category, nativeEmojis, customEmojis)
        }

        items(emojis) { emoji ->

            val style = when (size) {
                EmojiPickerSize.Small -> MaterialTheme.typography.headlineMedium
                EmojiPickerSize.Medium -> MaterialTheme.typography.headlineLarge
                EmojiPickerSize.Large -> MaterialTheme.typography.displaySmall
            }

            when (val skin = emoji.skins.first()) {
                is Skin.Local -> {
                    val painter = rememberImagePainter(skin.slackmoji)

                    Image(
                        painter,
                        contentDescription = null,
                        modifier = Modifier
                            .size(minWidth)
                            .clickable { onSelect(emoji) }
                    )
                }

                is Skin.Remote -> {
                    val painter = rememberAsyncImagePainter(skin.url)

                    Image(
                        painter,
                        contentDescription = null,
                        modifier = Modifier
                            .size(minWidth)
                            .clickable { onSelect(emoji) }
                    )
                }

                is Skin.Native -> {
                    Text(
                        skin.native,
                        style = style,
                        modifier = Modifier.clickable { onSelect(emoji) }
                    )
                }
            }
        }
    }
}

@Composable
private fun Footer(
    modifier: Modifier = Modifier,
    selected: Emoji?,
    minWidth: Dp,
    contentColor: Color,
    icon: @Composable () -> Unit,
    placeholder: @Composable () -> Unit,
) {
    if (selected != null) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {

            when (val skin = selected.skins.first()) {
                is Skin.Local -> {
                    val painter = rememberImagePainter(skin.slackmoji)

                    Image(
                        painter,
                        contentDescription = null,
                        modifier = Modifier.size(minWidth)
                    )
                }

                is Skin.Remote -> {
                    val painter = rememberAsyncImagePainter(skin.url)

                    Image(
                        painter,
                        contentDescription = null,
                        modifier = Modifier.size(minWidth)
                    )
                }

                is Skin.Native -> {
                    Text(
                        skin.native,
                        style = MaterialTheme.typography.displaySmall
                    )
                }
            }


            Column {
                Text(
                    selected.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = contentColor
                )
                Text(
                    ":${selected.id}:",
                    style = MaterialTheme.typography.labelLarge,
                    color = contentColor
                )

            }
        }
    } else {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            icon()
            placeholder()
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
private fun Modifier.adjustSize(size: EmojiPickerSize): Modifier {
    return this.then(
        when (size) {
            EmojiPickerSize.Small -> Modifier.size(200.dp)
            EmojiPickerSize.Medium -> Modifier.size(400.dp)
            EmojiPickerSize.Large -> Modifier.fillMaxSize()
        }
    )
}

private fun joinCategories(set: EmojiSet, custom: EmojiSet? = null): List<Category> {
    return set.categories.toMutableList().apply {

        add(
            Category(
                "Custom",
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
}


private fun getMinWidth(size: EmojiPickerSize): Dp = when (size) {
    EmojiPickerSize.Small -> 40.dp
    EmojiPickerSize.Medium -> 50.dp
    EmojiPickerSize.Large -> 60.dp
}

@Composable
private fun CategoryIcon(
    modifier: Modifier = Modifier,
    category: Category,
    isSelected: Boolean,
    selectedColor: Color,
    unselectedColor: Color
) {
    val painter = rememberImagePainter(category)

    if (category.type == CategoryType.Slackmoji) {
        Image(
            modifier = modifier.size(20.dp),
            painter = painter,
            contentDescription = null,
        )
    } else {
        Icon(
            modifier = modifier.size(20.dp),
            painter = painter,
            contentDescription = null,
            tint = if (isSelected) selectedColor else unselectedColor
        )

    }

}