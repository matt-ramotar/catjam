package com.dropbox.catjam.sample.android

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
import com.dropbox.catjam.Emoji
import com.dropbox.catjam.EmojiPicker
import com.dropbox.catjam.Slackmoji
import com.dropbox.catjam.models.Emoji
import com.dropbox.catjam.models.Slackmoji

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
                        Emoji(emoji = emoji!!, modifier = Modifier.size(64.dp))
                    } else {
                        Slackmoji(modifier = Modifier.size(64.dp), emoji = Slackmoji.Catjam)
                    }
                }
            }
        }
    }
}