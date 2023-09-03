package com.dropbox.catjam.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter


@Composable
expect fun rememberAsyncImagePainter(url: String): Painter


@Composable
expect fun rememberImagePainter(slackmoji: Slackmoji): Painter