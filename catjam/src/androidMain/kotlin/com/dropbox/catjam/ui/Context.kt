package com.dropbox.catjam.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.io.IOException

actual data class LoadJsonParams(
    val context: Context,
    val fileName: String
)

actual fun loadJson(
    params: LoadJsonParams
): String? {
    return try {
        val inputStream = params.context.assets.open(params.fileName)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        String(buffer, Charsets.UTF_8)
    } catch (error: IOException) {
        return null
    }
}


@Composable
actual fun getLoadJsonParams(fileName: String): LoadJsonParams {
    val context = LocalContext.current
    return LoadJsonParams(context, fileName)
}