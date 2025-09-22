package ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val PrimaryDark = Color(0xFF112B3C)
val Primary      = Color(0xFF205375)
val Accent       = Color(0xFFF66B0E)
private val Bg           = Color(0xFFEFEFEF)
private val Surface      = Color(0xFFFFFFFF)
private val TextPrimary  = Color(0xFF000000)
val TextSecondary = TextPrimary.copy(alpha = 0.7f)

private val LightColors = lightColorScheme(
  primary = Primary,
  onPrimary = Color.White,
  secondary = Accent,
  onSecondary = Color.White,
  background = Bg,
  onBackground = TextPrimary,
  surface = Surface,
  onSurface = TextPrimary,
  outline = Primary
)

@Composable
fun FocusTheme(content: @Composable () -> Unit) {
  MaterialTheme(
    colorScheme = LightColors,
    typography = Typography(),
    content = content
  )
}
