package navigation

import androidx.compose.runtime.*
import cafe.adriel.voyager.navigator.Navigator
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.screens.tasks.TasksScreen
import ui.theme.FocusTheme
import ui.theme.LocalThemeController
import ui.theme.rememberThemeController

@Composable
fun AppRoot() {
    val themeController = rememberThemeController()

    FocusTheme(isDarkTheme = themeController.isDarkTheme) {
        // Estado simplificado de autenticación
        var isLoggedIn by remember { mutableStateOf(false) }
        // Si no hay sesión, muestra Login; al iniciar sesión, reemplaza por Tabs
        Navigator(if (isLoggedIn) TabsRootScreen else LoginScreen) { nav ->
            // Permite que el Login cambie el estado
            CompositionLocalProvider(
                LocalAuthController provides AuthController(
                onLoginOk = {
                    isLoggedIn = true
                    nav.replaceAll(TabsRootScreen)
                },
                onLogout = {
                    isLoggedIn = false
                    nav.replaceAll(LoginScreen)
                }
            ),
            LocalThemeController provides themeController
            ) {
                nav.lastItem.Content()
            }
        }
    }
}

// Simple "controller" para comunicar Login -> AppRoot y Logout -> Login
open class AuthController(
    val onLoginOk: () -> Unit,
    val onLogout: () -> Unit
)

val LocalAuthController = compositionLocalOf<AuthController> {
    error("AuthController not provided")
}

@Preview
@Composable
fun SettingsScreenPreview() {
    CompositionLocalProvider(
        LocalAuthController provides AuthController(
            onLoginOk = { /* no-op para preview */ },
            onLogout = { /* no-op para preview */ }
        )
    ) {
        AppRoot()
    }
}
