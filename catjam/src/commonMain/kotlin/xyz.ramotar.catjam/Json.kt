package xyz.ramotar.catjam

import androidx.compose.runtime.Composable

@Composable
internal expect fun getParamsFor(fileName: String): JsonParams
internal expect class JsonParams

internal expect fun readJson(params: JsonParams): String