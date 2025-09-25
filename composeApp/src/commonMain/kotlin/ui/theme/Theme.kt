package ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val PrimaryDark = Color(0xFF112B3C)
val Primary      = Color(0xFF205375)
val Accent       = Color(0xFFF66B0E)
val Success      = Color(0xFF4CAF50) // Verde para tareas completadas
private val Bg           = Color(0xFFFFFFFF)
private val Surface      = Color(0xFFFFFFFF)
private val TextPrimary  = Color(0xFF000000)
val TextSecondary = TextPrimary.copy(alpha = 0.7f)

// Colores para modo oscuro
private val DarkBg = Color(0xFF0D1117) // Más oscuro para mejor contraste
private val DarkSurface = Color(0xFF161B22) // Superficie más clara que el fondo
private val DarkSurfaceVariant = Color(0xFF21262D) // Para cards y elementos
private val DarkTextPrimary = Color(0xFFF0F6FC) // Texto principal más suave
private val DarkTextSecondary = Color(0xFF8B949E) // Texto secundario más legible
private val DarkOutline = Color(0xFF30363D) // Bordes más visibles

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
  surfaceVariant = DarkSurfaceVariant,
  onSurfaceVariant = DarkTextSecondary,
  outline = DarkOutline,
  outlineVariant = DarkOutline.copy(alpha = 0.5f)
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
