package xyz.ramotar.catjam

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import xyz.ramotar.catjam.models.Category
import xyz.ramotar.catjam.models.Slackmoji

@Composable
internal expect fun rememberAsyncImagePainter(url: String): Painter


@Composable
internal expect fun rememberImagePainter(slackmoji: Slackmoji): Painter

@Composable
internal expect fun rememberImagePainter(category: Category): Painter