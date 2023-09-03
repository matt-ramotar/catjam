# Catjam

![](catjam.gif)

## Usage

```kotlin
EmojiPicker(
    size = EmojiPickerSize.Medium,
    colors = EmojiPickerDefaults.emojiPickerColors(),
    icon = { CustomIcon() },
    placeholder = { CustomPlaceholder() },
    customEmojis = customEmojis
) { emoji ->
    handleEmoji(emoji)
}

```