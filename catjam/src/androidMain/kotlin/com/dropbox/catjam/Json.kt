package com.dropbox.catjam

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

internal actual fun readJson(params: JsonParams): String {
    val inputStream = params.context.assets.open(params.fileName)
    val size = inputStream.available()
    val buffer = ByteArray(size)
    inputStream.read(buffer)
    inputStream.close()
    return String(buffer, Charsets.UTF_8)
}

internal actual class JsonParams(
    val context: Context,
    val fileName: String
)

@Composable
internal actual fun getParamsFor(fileName: String): JsonParams {
    val context = LocalContext.current
    return JsonParams(context, fileName)
}