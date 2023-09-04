# Catjam

![](catjam.gif)

## Including Catjam

### Android

```groovy
implementation "xyz.ramotar.catjam:catjam:0.0.1"
```

### Multiplatform (Common, JVM, Native)

```groovy
commonMain {
    dependencies {
        implementation("xyz.ramotar.catjam:catjam:0.0.1")
    }
}
```

## Using Catjam

### Picking an emoji

```kotlin
import xyz.ramotar.catjam.Emoji
import xyz.ramotar.catjam.EmojiPicker
import xyz.ramotar.catjam.Slackmoji
import xyz.ramotar.catjam.models.Emoji
import xyz.ramotar.catjam.models.EmojiPickerSize
import xyz.ramotar.catjam.EmojiPickerDefaults

EmojiPicker(
    size = EmojiPickerSize.Medium,
    colors = EmojiPickerDefaults.emojiPickerColors(),
    icon = { CustomIcon() },
    placeholder = { CustomPlaceholder() },
    customEmojis = customEmojis
) { emoji -> handleEmoji(emoji) }

```

### Rendering an emoji

```kotlin
import xyz.ramotar.catjam.Emoji
import xyz.ramotar.catjam.EmojiPicker
import xyz.ramotar.catjam.Slackmoji
import xyz.ramotar.catjam.models.Emoji
import xyz.ramotar.catjam.models.Slackmoji

@Composable
fun AnyEmoji(emoji: Emoji) {
    Emoji(emoji)
}

@Composable
fun Catjam() {
    Emoji("catjam")
}
```

## License

```text
Copyright (c) 2023 Matt Ramotar.
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
```
