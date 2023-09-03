package xyz.ramotar.catjam

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import xyz.ramotar.catjam.models.Emoji
import xyz.ramotar.catjam.models.Skin
import xyz.ramotar.catjam.models.Slackmoji

@Composable
fun Emoji(modifier: Modifier = Modifier, emoji: Emoji, onClick: () -> Unit = {}) {
    when (val skin = emoji.skins.first()) {
        is Skin.Local -> {
            Slackmoji(modifier, skin.slackmoji, onClick)
        }

        is Skin.Remote -> {
            val painter = rememberAsyncImagePainter(skin.url)

            Image(
                painter,
                contentDescription = null,
                modifier = modifier.clickable { onClick() }
            )
        }

        is Skin.Native -> {
            Text(
                skin.native,
                style = MaterialTheme.typography.displaySmall,
                modifier = modifier.clickable { onClick() }
            )
        }
    }

}

@Composable
fun Slackmoji(modifier: Modifier = Modifier, emoji: Slackmoji, onClick: () -> Unit = {}) {
    val painter = rememberImagePainter(emoji)

    Image(
        painter,
        contentDescription = null,
        modifier = modifier.clickable { onClick() }
    )
}

@Composable
fun Emoji(modifier: Modifier, id: String, onClick: () -> Unit = {}) {
    val emoji = mapEmojis()[id]

    if (emoji != null) {
        Emoji(modifier = modifier, emoji = emoji, onClick = onClick)
    }
}

