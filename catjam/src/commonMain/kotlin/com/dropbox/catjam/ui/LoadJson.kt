package com.dropbox.catjam.ui

import androidx.compose.runtime.Composable

@Composable

expect fun getLoadJsonParams(fileName: String): LoadJsonParams
expect class LoadJsonParams

expect fun loadJson(params: LoadJsonParams): String?
