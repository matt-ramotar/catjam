package com.dropbox.catjam.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.json.Json

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmojiPicker(
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
    onSelect: (emoji: Emoji) -> Unit
) {
    val set = loadEmojis()

    EmojiPicker(
        modifier = modifier,
        set = set,
        size = size,
        containerColor = containerColor,
        contentColor = contentColor,
        accentColor = accentColor,
        textFieldContainerColor = textFieldContainerColor,
        textFieldContentColor = textFieldContentColor,
        onSelect = onSelect,
        icon = icon,
        placeholder = placeholder,
        custom = custom
    )
}


@Composable
private fun loadEmojis(): State<Set?> {
    val state = MutableStateFlow<Set?>(null)

    val serializer = Json {
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true
    }
    val params = getLoadJsonParams("native.json")

    LaunchedEffect(Unit) {
        val json = requireNotNull(loadJson(params))
        val set = serializer.decodeFromString<Set>(json)
        state.value = set
    }

    return state.collectAsState()
}

@Composable
private fun EmojiPicker(
    modifier: Modifier,
    set: State<Set?>,
    size: PickerSize,
    containerColor: Color,
    contentColor: Color,
    accentColor: Color,
    textFieldContainerColor: Color,
    textFieldContentColor: Color,
    onSelect: (emoji: Emoji) -> Unit,
    icon: @Composable () -> Unit,
    placeholder: @Composable () -> Unit,
    custom: Set?
) {
    var searchInput: String by remember { mutableStateOf("") }
    var category: Category by remember { mutableStateOf(data.set.categories[0]) }
    var selected: Emoji? by remember { mutableStateOf(null) }

    val categories = getCategories(data, custom)
    val minWidth = getMinWidth(size)

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.background(containerColor).adjustSize(size)
    ) {
        CategoriesRow(categories, category, accentColor, contentColor)
        SearchTextField(
            searchInput,
            textFieldContainerColor,
            textFieldContentColor,
            accentColor,
            onValueChange = { searchInput = it })
        EmojisGrid(data, searchInput, size, custom, category, minWidth, onSelect, selected)
        SelectedEmojiFooter(selected, minWidth, onSelect, icon, placeholder, contentColor)
    }
}

@Composable
fun CategoriesRow(
    categories: List<Category>,
    currentCategory: Category,
    accentColor: Color,
    contentColor: Color
) {
    // TODO: Logic for categories row UI
}

@Composable
fun SearchTextField(
    value: String,
    containerColor: Color,
    textColor: Color,
    cursorColor: Color,
    onValueChange: (String) -> Unit
) {
    // TODO: Logic for search text field UI
}

@Composable
fun EmojisGrid(
    data: Catjam.Data,
    searchInput: String,
    size: PickerSize,
    custom: Set?,
    category: Category,
    minWidth: Dp,
    onSelect: (emoji: Emoji) -> Unit,
    selected: Emoji?
) {
    // TODO: Logic for emojis grid UI
}

@Composable
fun SelectedEmojiFooter(
    selected: Emoji?,
    minWidth: Dp,
    onSelect: (emoji: Emoji) -> Unit,
    icon: @Composable () -> Unit,
    placeholder: @Composable () -> Unit,
    contentColor: Color
) {
    // TODO: Logic for the footer showing the selected emoji
}

// ... Additional helper functions like getCategories, getMinWidth, adjustSize and others.


@Composable
private fun DefaultIcon() {
    Text("☝️", style = MaterialTheme.typography.displaySmall)
}

@Composable
private fun DefaultPlaceholder(color: Color) {
    Text("Pick an emoji...️", style = MaterialTheme.typography.titleLarge, color = color)
}
