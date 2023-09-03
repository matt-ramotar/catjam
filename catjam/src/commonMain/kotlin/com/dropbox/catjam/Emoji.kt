package com.dropbox.catjam

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dropbox.catjam.models.Emoji
import com.dropbox.catjam.models.Skin
import com.dropbox.catjam.models.Slackmoji

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

