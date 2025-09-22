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

// Colores para modo oscuro
private val DarkBg = Color(0xFF121212)
private val DarkSurface = Color(0xFF1E1E1E)
private val DarkTextPrimary = Color(0xFFFFFFFF)
private val DarkTextSecondary = DarkTextPrimary.copy(alpha = 0.7f)

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

private val DarkColors = darkColorScheme(
  primary = Primary,
  onPrimary = Color.White,
  secondary = Accent,
  onSecondary = Color.White,
  background = DarkBg,
  onBackground = DarkTextPrimary,
  surface = DarkSurface,
  onSurface = DarkTextPrimary,
  outline = Primary
)

@Composable
fun FocusTheme(
  isDarkTheme: Boolean = false,
  content: @Composable () -> Unit
) {
  MaterialTheme(
    colorScheme = if (isDarkTheme) DarkColors else LightColors,
    typography = Typography(),
    content = content
  )
}
