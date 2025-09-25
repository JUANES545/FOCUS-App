package ui.theme

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable

// Enum para los modos de tema
enum class ThemeMode {
    LIGHT,
    DARK
}

// Clase para controlar el tema
class ThemeController(
    private val _isDarkTheme: MutableState<Boolean>
) {
    val isDarkTheme: Boolean
        get() = _isDarkTheme.value

    val themeMode: ThemeMode
        get() = if (_isDarkTheme.value) ThemeMode.DARK else ThemeMode.LIGHT

    fun toggleTheme() {
        _isDarkTheme.value = !_isDarkTheme.value
    }

    fun setThemeMode(mode: ThemeMode) {
        _isDarkTheme.value = mode == ThemeMode.DARK
    }
}

// Composable para crear el ThemeController
@Composable
fun rememberThemeController(): ThemeController {
    val isDarkTheme = rememberSaveable { mutableStateOf(false) }
    return remember { ThemeController(isDarkTheme) }
}

// CompositionLocal para acceder al ThemeController desde cualquier parte de la app
val LocalThemeController = compositionLocalOf<ThemeController> {
    error("ThemeController not provided")
}
