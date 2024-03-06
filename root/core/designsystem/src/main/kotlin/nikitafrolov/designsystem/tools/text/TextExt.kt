package nikitafrolov.designsystem.tools.text

val Int.text: Text
    get() = Text.Res(this)

val String.text: Text
    get() = Text.Str(this)

fun Int?.textOrEmpty(): Text = if (this != null) Text.Res(this) else Text.Str("")

fun String?.textOrEmpty(): Text = Text.Str(this.orEmpty())
