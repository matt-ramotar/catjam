package com.dropbox.catjam

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import com.dropbox.catjam.models.Category
import com.dropbox.catjam.models.Slackmoji

@Composable
internal expect fun rememberAsyncImagePainter(url: String): Painter


@Composable
internal expect fun rememberImagePainter(slackmoji: Slackmoji): Painter

@Composable
internal expect fun rememberImagePainter(category: Category): Painter