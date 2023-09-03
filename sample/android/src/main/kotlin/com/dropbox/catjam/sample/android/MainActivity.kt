package com.dropbox.catjam.sample.android

import android.content.Context
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
import com.dropbox.catjam.ui.Category
import com.dropbox.catjam.ui.Emoji
import com.dropbox.catjam.ui.Picker
import com.dropbox.catjam.ui.Set
import com.dropbox.catjam.ui.Skin
import com.dropbox.catjam.ui.Slackmoji
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private fun loadJson(context: Context, fileName: String): String? {
        return try {
            val inputStream = context.assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charsets.UTF_8)
        } catch (error: IOException) {
            println(error)
            return null
        }
    }

    private val customEmojis = Set(
        categories = listOf(
            Category(
                "slack",
                emojis = listOf("catjam", "catjam_rainbow")
            )
        ),
        emojis = mutableMapOf<String, Emoji>().apply {
            put(
                "catjam", Emoji(
                    "catjam", "Catjam", listOf("catjam"), skins = listOf(
                        Skin.Remote("https://emojis.slackmojis.com/emojis/images/1643514974/10003/catjam.gif?1643514974")
                    ), version = 1
                )
            )

            put(
                "catjam_rainbow", Emoji(
                    "catjam_rainbow",
                    "Catjam Rainbow",
                    listOf("catjam_rainbow"),
                    skins = listOf(
                        Skin.Remote("https://emojis.slackmojis.com/emojis/images/1643516952/30079/catjam_rainbow.gif?1643516952")
                    ),
                    version = 1
                )
            )
        },
        aliases = mutableMapOf()
    )

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

                    Picker(custom = customEmojis) {
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