package xyz.ramotar.catjam.sample.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import xyz.ramotar.catjam.Emoji
import xyz.ramotar.catjam.EmojiPicker
import xyz.ramotar.catjam.Slackmoji
import xyz.ramotar.catjam.models.Emoji
import xyz.ramotar.catjam.models.Slackmoji

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            var emoji: Emoji? by remember { mutableStateOf(null) }

            var showPicker: Boolean by remember {
                mutableStateOf(false)
            }

            Column {
                Button(onClick = { showPicker = true }) {
                    Text(text = "Show Picker")
                }

                if (showPicker) {
                    EmojiPicker {
                        emoji = it
                        showPicker = false
                    }
                } else {
                    if (emoji != null) {
                        Emoji(modifier = Modifier.size(64.dp), emoji = emoji!!)
                    } else {
                        Slackmoji(modifier = Modifier.size(64.dp), emoji = Slackmoji.Catjam)
                    }
                }
            }
        }
    }
}