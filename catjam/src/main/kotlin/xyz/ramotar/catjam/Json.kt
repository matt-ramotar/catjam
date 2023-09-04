package xyz.ramotar.catjam

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

internal fun readJson(params: JsonParams): String {
    val inputStream = params.context.assets.open(params.fileName)
    val size = inputStream.available()
    val buffer = ByteArray(size)
    inputStream.read(buffer)
    inputStream.close()
    return String(buffer, Charsets.UTF_8)
}

internal class JsonParams(
    val context: Context,
    val fileName: String
)

@Composable
internal fun getParamsFor(fileName: String): JsonParams {
    val context = LocalContext.current
    return JsonParams(context, fileName)
}